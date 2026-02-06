       IDENTIFICATION DIVISION.
       PROGRAM-ID. DEPOSIT.

       ENVIRONMENT DIVISION.
       INPUT-OUTPUT SECTION.
       FILE-CONTROL.
           SELECT INFILE ASSIGN TO "input.txt"
               ORGANIZATION IS LINE SEQUENTIAL.
           SELECT ACCFILE ASSIGN TO "accounts.dat"
               ORGANIZATION IS LINE SEQUENTIAL.
           SELECT TEMPFILE ASSIGN TO "temp.dat"
               ORGANIZATION IS LINE SEQUENTIAL.
           SELECT OUTFILE ASSIGN TO "output.txt"
               ORGANIZATION IS LINE SEQUENTIAL.

       DATA DIVISION.
       FILE SECTION.

       FD INFILE.
       01 IN-REC              PIC X(30).

       FD ACCFILE.
       01 ACC-REC             PIC X(30).

       FD TEMPFILE.
       01 TEMP-REC            PIC X(40).

       FD OUTFILE.
       01 OUT-REC             PIC X(60).

       WORKING-STORAGE SECTION.

       01 WS-ACC              PIC X(10).
       01 WS-AMT-TXT          PIC X(15).
       01 WS-AMT              PIC 9(10).

       01 FILE-ACC            PIC X(10).
       01 FILE-BAL-TXT        PIC X(15).
       01 FILE-BAL            PIC 9(10).

       01 NEW-BAL-TXT         PIC Z(10).

       01 EOF-FLAG            PIC X VALUE 'N'.
       01 FOUND-FLAG          PIC X VALUE 'N'.

       PROCEDURE DIVISION.

           OPEN INPUT INFILE
           READ INFILE INTO IN-REC
           CLOSE INFILE

           UNSTRING IN-REC DELIMITED BY "|"
               INTO WS-ACC WS-AMT-TXT
           END-UNSTRING

           MOVE FUNCTION NUMVAL(WS-AMT-TXT) TO WS-AMT

           OPEN INPUT ACCFILE
           OPEN OUTPUT TEMPFILE

           PERFORM UNTIL EOF-FLAG = 'Y'
               READ ACCFILE
                   AT END MOVE 'Y' TO EOF-FLAG
               NOT AT END

                   UNSTRING ACC-REC DELIMITED BY "|"
                       INTO FILE-ACC FILE-BAL-TXT
                   END-UNSTRING

                   MOVE FUNCTION NUMVAL(FILE-BAL-TXT) TO FILE-BAL

                   IF FUNCTION TRIM(FILE-ACC) = FUNCTION TRIM(WS-ACC)
                       ADD WS-AMT TO FILE-BAL
                       MOVE 'Y' TO FOUND-FLAG
                   END-IF

                   MOVE FILE-BAL TO NEW-BAL-TXT

                   MOVE SPACES TO TEMP-REC
                   STRING FUNCTION TRIM(FILE-ACC) "|"
                          FUNCTION TRIM(NEW-BAL-TXT)
                          INTO TEMP-REC
                   END-STRING

                   WRITE TEMP-REC
               END-READ
           END-PERFORM

           CLOSE ACCFILE
           CLOSE TEMPFILE

           OPEN OUTPUT OUTFILE
           MOVE SPACES TO OUT-REC
           IF FOUND-FLAG = 'Y'
               STRING "DEPOSIT SUCCESS FOR ACCOUNT " WS-ACC
                      INTO OUT-REC
               END-STRING
           ELSE
               MOVE "ACCOUNT NOT FOUND" TO OUT-REC
           END-IF
           WRITE OUT-REC
           CLOSE OUTFILE

           STOP RUN.


