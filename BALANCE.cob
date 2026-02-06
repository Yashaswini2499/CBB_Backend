       IDENTIFICATION DIVISION.
       PROGRAM-ID. BALANCE.

       ENVIRONMENT DIVISION.
       INPUT-OUTPUT SECTION.
       FILE-CONTROL.
           SELECT ACCFILE ASSIGN TO "accounts.dat"
               ORGANIZATION IS LINE SEQUENTIAL.
           SELECT INFILE ASSIGN TO "input.txt"
               ORGANIZATION IS LINE SEQUENTIAL.
           SELECT OUTFILE ASSIGN TO "output.txt"
               ORGANIZATION IS LINE SEQUENTIAL.

       DATA DIVISION.
       FILE SECTION.

       FD ACCFILE.
       01 ACC-LINE PIC X(50).

       FD INFILE.
       01 IN-REC PIC X(20).

       FD OUTFILE.
       01 OUT-REC PIC X(50).

       WORKING-STORAGE SECTION.
       01 IN-ACC     PIC X(12).
       01 FILE-ACC   PIC X(12).
       01 FILE-BAL   PIC X(20).
       01 FOUND      PIC X VALUE 'N'.

       PROCEDURE DIVISION.

           MOVE SPACES TO OUT-REC

           OPEN INPUT INFILE
           READ INFILE INTO IN-REC
           MOVE FUNCTION TRIM(IN-REC) TO IN-ACC
           CLOSE INFILE

           OPEN INPUT ACCFILE

           PERFORM UNTIL FOUND = 'Y'
               READ ACCFILE
                   AT END EXIT PERFORM
               END-READ

               UNSTRING ACC-LINE DELIMITED BY '|'
                   INTO FILE-ACC FILE-BAL
               END-UNSTRING

               IF FUNCTION TRIM(FILE-ACC) = IN-ACC
                   MOVE 'Y' TO FOUND
               END-IF
           END-PERFORM

           CLOSE ACCFILE

           OPEN OUTPUT OUTFILE

           IF FOUND = 'Y'
               MOVE SPACES TO OUT-REC
               STRING "BALANCE=" DELIMITED BY SIZE
                      FILE-BAL  DELIMITED BY SIZE
                      INTO OUT-REC
               END-STRING
               WRITE OUT-REC
           ELSE
               MOVE "ACCOUNT NOT FOUND" TO OUT-REC
               WRITE OUT-REC
           END-IF

           CLOSE OUTFILE
           STOP RUN.

