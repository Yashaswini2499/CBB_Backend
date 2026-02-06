       IDENTIFICATION DIVISION.
       PROGRAM-ID. LOAN-ELIGIBILITY.

       ENVIRONMENT DIVISION.
       INPUT-OUTPUT SECTION.
       FILE-CONTROL.
           SELECT INFILE ASSIGN TO "input.txt"
               ORGANIZATION IS LINE SEQUENTIAL.
           SELECT OUTFILE ASSIGN TO "output.txt"
               ORGANIZATION IS LINE SEQUENTIAL.

       DATA DIVISION.
       FILE SECTION.

       FD INFILE.
       01 IN-REC            PIC X(50).

       FD OUTFILE.
       01 OUT-REC           PIC X(80).

       WORKING-STORAGE SECTION.

       01 WS-USER           PIC X(10).
       01 WS-SCORE-TXT      PIC X(10).
       01 WS-SCORE          PIC 9(5).

       PROCEDURE DIVISION.

           OPEN INPUT INFILE
           READ INFILE INTO IN-REC
           CLOSE INFILE

           UNSTRING IN-REC DELIMITED BY "|"
               INTO WS-USER WS-SCORE-TXT
           END-UNSTRING

           MOVE FUNCTION NUMVAL(WS-SCORE-TXT) TO WS-SCORE

           OPEN OUTPUT OUTFILE
           MOVE SPACES TO OUT-REC

           IF WS-SCORE >= 700
               STRING "USER " WS-USER
                      " LOAN STATUS: ELIGIBLE"
                      INTO OUT-REC
           ELSE
               STRING "USER " WS-USER
                      " LOAN STATUS: NOT ELIGIBLE"
                      INTO OUT-REC
           END-IF

           WRITE OUT-REC
           CLOSE OUTFILE

           STOP RUN.
