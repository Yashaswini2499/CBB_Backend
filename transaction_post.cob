       IDENTIFICATION DIVISION.
       PROGRAM-ID. TRANSACTION-POST.

       ENVIRONMENT DIVISION.
       INPUT-OUTPUT SECTION.
       FILE-CONTROL.
           SELECT INFILE ASSIGN TO "input.txt"
               ORGANIZATION IS LINE SEQUENTIAL.
           SELECT TXNFILE ASSIGN TO "transactions.dat"
               ORGANIZATION IS LINE SEQUENTIAL.

       DATA DIVISION.
       FILE SECTION.

       FD INFILE.
       01 IN-REC              PIC X(80).

       FD TXNFILE.
       01 TXN-REC             PIC X(120).

       WORKING-STORAGE SECTION.

       01 WS-ACC              PIC X(10).
       01 WS-TYPE             PIC X(15).
       01 WS-AMT-TXT          PIC X(15).
       01 WS-AMT              PIC 9(10).

       01 WS-TIMESTAMP        PIC X(20).
       01 WS-DATE             PIC 9(8).
       01 WS-TIME             PIC 9(6).

       PROCEDURE DIVISION.

           ACCEPT WS-DATE FROM DATE YYYYMMDD
           ACCEPT WS-TIME FROM TIME

           STRING WS-DATE "-" WS-TIME INTO WS-TIMESTAMP

           OPEN INPUT INFILE
           READ INFILE INTO IN-REC
           CLOSE INFILE

           UNSTRING IN-REC DELIMITED BY "|"
               INTO WS-ACC WS-TYPE WS-AMT-TXT
           END-UNSTRING

           MOVE FUNCTION NUMVAL(WS-AMT-TXT) TO WS-AMT

           OPEN OUTPUT TXNFILE
	   CLOSE TXNFILE
	   OPEN EXTEND TXNFILE
           MOVE SPACES TO TXN-REC
           STRING FUNCTION TRIM(WS-ACC) "|"
                  FUNCTION TRIM(WS-TYPE) "|"
                  FUNCTION TRIM(WS-AMT-TXT) "|"
                  WS-TIMESTAMP
                  INTO TXN-REC
           END-STRING

           WRITE TXN-REC

           CLOSE TXNFILE

           STOP RUN.
