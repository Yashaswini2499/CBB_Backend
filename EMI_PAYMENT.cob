       IDENTIFICATION DIVISION.
       PROGRAM-ID. EMI-PAYMENT.

       ENVIRONMENT DIVISION.
       INPUT-OUTPUT SECTION.
       FILE-CONTROL.
           SELECT INFILE ASSIGN TO "input.txt"
               ORGANIZATION IS LINE SEQUENTIAL.
           SELECT ACCFILE ASSIGN TO "accounts.dat"
               ORGANIZATION IS LINE SEQUENTIAL.
           SELECT TEMPACC ASSIGN TO "temp_acc.dat"
               ORGANIZATION IS LINE SEQUENTIAL.
           SELECT LOANFILE ASSIGN TO "loans.dat"
               ORGANIZATION IS LINE SEQUENTIAL.
           SELECT TEMPLOAN ASSIGN TO "temp_loan.dat"
               ORGANIZATION IS LINE SEQUENTIAL.
           SELECT OUTFILE ASSIGN TO "output.txt"
               ORGANIZATION IS LINE SEQUENTIAL.

       DATA DIVISION.
       FILE SECTION.

       FD INFILE.
       01 IN-REC              PIC X(80).

       FD ACCFILE.
       01 ACC-REC             PIC X(50).

       FD TEMPACC.
       01 TACC-REC            PIC X(50).

       FD LOANFILE.
       01 LOAN-REC            PIC X(80).

       FD TEMPLOAN.
       01 TLOAN-REC           PIC X(80).

       FD OUTFILE.
       01 OUT-REC             PIC X(80).

       WORKING-STORAGE SECTION.

       01 WS-ACC              PIC X(10).
       01 WS-LOANID           PIC X(10).
       01 WS-EMI-TXT          PIC X(15).
       01 WS-EMI              PIC 9(10).

       01 FILE-ACC            PIC X(10).
       01 FILE-BAL-TXT        PIC X(15).
       01 FILE-BAL            PIC 9(10).

       01 LOAN-ID             PIC X(10).
       01 LOAN-ACC            PIC X(10).
       01 LOAN-AMT-TXT        PIC X(15).
       01 LOAN-AMT            PIC 9(10).

       01 BAL-OUT             PIC X(15).
       01 LOAN-OUT            PIC X(15).

       01 FOUND-ACC           PIC X VALUE 'N'.
       01 FAIL-FLAG           PIC X VALUE 'N'.
       01 EOF-FLAG            PIC X VALUE 'N'.

       PROCEDURE DIVISION.

           OPEN INPUT INFILE
           READ INFILE INTO IN-REC
           CLOSE INFILE

           UNSTRING IN-REC DELIMITED BY "|"
               INTO WS-ACC WS-LOANID WS-EMI-TXT
           END-UNSTRING

           MOVE FUNCTION NUMVAL(WS-EMI-TXT) TO WS-EMI

           OPEN INPUT ACCFILE
           OPEN OUTPUT TEMPACC

           PERFORM UNTIL EOF-FLAG = 'Y'
               READ ACCFILE
                   AT END MOVE 'Y' TO EOF-FLAG
               NOT AT END

                   UNSTRING ACC-REC DELIMITED BY "|"
                       INTO FILE-ACC FILE-BAL-TXT
                   END-UNSTRING

                   MOVE FUNCTION NUMVAL(FILE-BAL-TXT) TO FILE-BAL

                   IF FUNCTION TRIM(FILE-ACC) = FUNCTION TRIM(WS-ACC)
                       MOVE 'Y' TO FOUND-ACC
                       IF FILE-BAL < WS-EMI
                           MOVE 'Y' TO FAIL-FLAG
                       ELSE
                           SUBTRACT WS-EMI FROM FILE-BAL
                       END-IF
                   END-IF

                   MOVE SPACES TO BAL-OUT
                   MOVE FILE-BAL TO BAL-OUT

                   MOVE SPACES TO TACC-REC
                   STRING FUNCTION TRIM(FILE-ACC) "|"
                          FUNCTION TRIM(BAL-OUT)
                          INTO TACC-REC
                   END-STRING
                   WRITE TACC-REC
               END-READ
           END-PERFORM

           CLOSE ACCFILE
           CLOSE TEMPACC

           IF FAIL-FLAG = 'N'
               MOVE 'N' TO EOF-FLAG
               OPEN INPUT LOANFILE
               OPEN OUTPUT TEMPLOAN

               PERFORM UNTIL EOF-FLAG = 'Y'
                   READ LOANFILE
                       AT END MOVE 'Y' TO EOF-FLAG
                   NOT AT END

                       UNSTRING LOAN-REC DELIMITED BY "|"
                           INTO LOAN-ID LOAN-ACC LOAN-AMT-TXT
                       END-UNSTRING

                       MOVE FUNCTION NUMVAL(LOAN-AMT-TXT) TO LOAN-AMT

                       IF FUNCTION TRIM(LOAN-ID) = FUNCTION TRIM(WS-LOANID)
                           SUBTRACT WS-EMI FROM LOAN-AMT
                       END-IF

                       MOVE SPACES TO LOAN-OUT
                       MOVE LOAN-AMT TO LOAN-OUT

                       MOVE SPACES TO TLOAN-REC
                       STRING FUNCTION TRIM(LOAN-ID) "|"
                              FUNCTION TRIM(LOAN-ACC) "|"
                              FUNCTION TRIM(LOAN-OUT)
                              INTO TLOAN-REC
                       END-STRING
                       WRITE TLOAN-REC
                   END-READ
               END-PERFORM

               CLOSE LOANFILE
               CLOSE TEMPLOAN
           END-IF

           OPEN OUTPUT OUTFILE

           IF FAIL-FLAG = 'Y'
               MOVE "EMI PAYMENT FAILED - LOW BALANCE" TO OUT-REC
           ELSE
               MOVE "EMI PAYMENT SUCCESS" TO OUT-REC
           END-IF

           WRITE OUT-REC
           CLOSE OUTFILE

           STOP RUN.
