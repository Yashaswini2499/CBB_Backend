       IDENTIFICATION DIVISION.
       PROGRAM-ID. INTEREST.

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
       01 IN-REC              PIC X(50).

       FD ACCFILE.
       01 ACC-REC             PIC X(30).

       FD TEMPFILE.
       01 TEMP-REC            PIC X(40).

       FD OUTFILE.
       01 OUT-REC             PIC X(80).

       WORKING-STORAGE SECTION.

       01 WS-ACC              PIC X(10).
       01 WS-RATE-TXT         PIC X(10).
       01 WS-RATE             PIC 9(3)V99 VALUE 0.
       01 WS-INT              PIC 9(10)V99 VALUE 0.

       01 FILE-ACC            PIC X(10).
       01 FILE-BAL-TXT        PIC X(15).
       01 FILE-BAL            PIC 9(12)V99 VALUE 0.

       01 NEW-BAL             PIC 9(12)V99 VALUE 0.
       01 NEW-BAL-TXT         PIC Z(12).

       01 EOF-FLAG            PIC X VALUE 'N'.
       01 FOUND               PIC X VALUE 'N'.

       PROCEDURE DIVISION.

           OPEN INPUT INFILE
           READ INFILE INTO IN-REC
           CLOSE INFILE

           UNSTRING IN-REC DELIMITED BY "|"
               INTO WS-ACC WS-RATE-TXT
           END-UNSTRING

           MOVE FUNCTION NUMVAL(WS-RATE-TXT) TO WS-RATE

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
                       MOVE 'Y' TO FOUND
                       COMPUTE WS-INT = FILE-BAL * WS-RATE / 100
                       COMPUTE NEW-BAL = FILE-BAL + WS-INT
                   ELSE
                       MOVE FILE-BAL TO NEW-BAL
                   END-IF

                   MOVE NEW-BAL TO NEW-BAL-TXT

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

           IF FOUND = 'Y'
               STRING "INTEREST APPLIED TO ACCOUNT "
                      FUNCTION TRIM(WS-ACC)
                      INTO OUT-REC
           ELSE
               MOVE "ACCOUNT NOT FOUND" TO OUT-REC
           END-IF

           WRITE OUT-REC
           CLOSE OUTFILE

           STOP RUN.

