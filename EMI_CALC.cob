       IDENTIFICATION DIVISION.
       PROGRAM-ID. EMI-CALC.

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
       01 IN-REC            PIC X(80).

       FD OUTFILE.
       01 OUT-REC           PIC X(80).

       WORKING-STORAGE SECTION.

       01 WS-P-TXT          PIC X(15).
       01 WS-RATE-TXT       PIC X(10).
       01 WS-N-TXT          PIC X(10).

       01 WS-P              PIC 9(10)V99.
       01 WS-RATE           PIC 9(3)V9999.
       01 WS-N              PIC 9(5).

       01 WS-R              COMP-2.
       01 WS-ONEPLUS        COMP-2.
       01 WS-POWER          COMP-2.
       01 WS-EMI            COMP-2.

       01 WS-EMI-DISP       PIC Z(10)9.99.

       PROCEDURE DIVISION.

           OPEN INPUT INFILE
           READ INFILE INTO IN-REC
           CLOSE INFILE

           UNSTRING IN-REC DELIMITED BY "|"
               INTO WS-P-TXT WS-RATE-TXT WS-N-TXT
           END-UNSTRING

           MOVE FUNCTION NUMVAL(WS-P-TXT) TO WS-P
           MOVE FUNCTION NUMVAL(WS-RATE-TXT) TO WS-RATE
           MOVE FUNCTION NUMVAL(WS-N-TXT) TO WS-N

           COMPUTE WS-R = WS-RATE / 12 / 100
           COMPUTE WS-ONEPLUS = 1 + WS-R
           COMPUTE WS-POWER = WS-ONEPLUS ** WS-N

           COMPUTE WS-EMI = (WS-P * WS-R * WS-POWER) / (WS-POWER - 1)

           MOVE WS-EMI TO WS-EMI-DISP

           OPEN OUTPUT OUTFILE
           MOVE SPACES TO OUT-REC
           STRING "EMI = " WS-EMI-DISP
               INTO OUT-REC
           END-STRING
           WRITE OUT-REC
           CLOSE OUTFILE

           STOP RUN.

