       IDENTIFICATION DIVISION.
       PROGRAM-ID. LEDGER-UPDATE.

       ENVIRONMENT DIVISION.
       INPUT-OUTPUT SECTION.
       FILE-CONTROL.
           SELECT ACCFILE ASSIGN TO "accounts.dat"
               ORGANIZATION IS LINE SEQUENTIAL.
           SELECT LEDGERFILE ASSIGN TO "ledger.dat"
               ORGANIZATION IS LINE SEQUENTIAL.
           SELECT OUTFILE ASSIGN TO "output.txt"
               ORGANIZATION IS LINE SEQUENTIAL.

       DATA DIVISION.
       FILE SECTION.

       FD ACCFILE.
       01 ACC-REC              PIC X(50).

       FD LEDGERFILE.
       01 LEDGER-REC           PIC X(50).

       FD OUTFILE.
       01 OUT-REC              PIC X(80).

       WORKING-STORAGE SECTION.

       01 WS-ACC               PIC X(10).
       01 WS-BAL-TXT           PIC X(15).
       01 WS-SUM               PIC 9(12) VALUE 0.

       01 WS-LABEL             PIC X(10).
       01 WS-LEDGER-TXT        PIC X(15).
       01 WS-LEDGER            PIC 9(12) VALUE 0.

       01 EOF-FLAG             PIC X VALUE 'N'.

       PROCEDURE DIVISION.

           MOVE 0 TO WS-SUM
           MOVE 'N' TO EOF-FLAG

           OPEN INPUT ACCFILE
           PERFORM UNTIL EOF-FLAG = 'Y'
               READ ACCFILE
                   AT END
                       MOVE 'Y' TO EOF-FLAG
                   NOT AT END
                       UNSTRING ACC-REC DELIMITED BY "|"
                           INTO WS-ACC WS-BAL-TXT
                       END-UNSTRING
                       ADD FUNCTION NUMVAL(WS-BAL-TXT) TO WS-SUM
               END-READ
           END-PERFORM
           CLOSE ACCFILE

           OPEN INPUT LEDGERFILE
           READ LEDGERFILE INTO LEDGER-REC
           CLOSE LEDGERFILE

           UNSTRING LEDGER-REC DELIMITED BY "|"
               INTO WS-LABEL WS-LEDGER-TXT
           END-UNSTRING

           MOVE FUNCTION NUMVAL(WS-LEDGER-TXT) TO WS-LEDGER

           OPEN OUTPUT OUTFILE
           MOVE SPACES TO OUT-REC

           IF WS-SUM = WS-LEDGER
               MOVE "LEDGER CONSISTENT - OK" TO OUT-REC
           ELSE
               MOVE "LEDGER MISMATCH - ERROR" TO OUT-REC
           END-IF

           WRITE OUT-REC
           CLOSE OUTFILE

           STOP RUN.
