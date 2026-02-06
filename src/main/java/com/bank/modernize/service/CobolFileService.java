//package com.bank.modernize.service;
//
//import com.bank.modernize.entity.Account;
//import com.bank.modernize.entity.Transaction;
//import com.bank.modernize.enums.TxnStatus;
//import com.bank.modernize.enums.TxnType;
//import com.bank.modernize.repository.AccountRepository;
//import com.bank.modernize.repository.TransactionRepository;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.nio.file.*;
//import java.sql.Timestamp;
//import java.util.List;
//
//@Service
//public class CobolFileService {
//
//    private static final Logger logger =
//            LoggerFactory.getLogger(CobolFileService.class);
//
//    @Autowired
//    private TransactionRepository transactionRepo;
//
//    @Autowired
//    private AccountRepository accountRepo;
//
//    private static final String INPUT_FOLDER =
//            "C:\\corebank\\cobol\\input\\";
//    private static final String PROCESSED_FOLDER =
//            "C:\\corebank\\cobol\\processed\\";
//
//    // ================= SCHEDULER =================
//
//    @Scheduled(fixedDelay = 5000)
//    public void processTransactionFiles() {
//
//        logger.info("Scheduler running...");
//
//        try {
//            Path inputPath = Paths.get(INPUT_FOLDER);
//            Path processedPath = Paths.get(PROCESSED_FOLDER);
//
//            Files.createDirectories(processedPath);
//            logger.info("Processed path: {}", processedPath);
//
//            try (DirectoryStream<Path> files =
//                         Files.newDirectoryStream(inputPath, "*.txt")) {
//
//                for (Path file : files) {
//                    logger.info("Processing file: {}", file.getFileName());
//
//                    List<String> lines = Files.readAllLines(file);
//
//                    if (lines.size() <= 1) {
//                        logger.warn("File {} has no records", file.getFileName());
//                        moveToProcessed(file, processedPath);
//                        continue;
//                    }
//
//                    // Skip header
//                    for (int i = 1; i < lines.size(); i++) {
//                        try {
//                            processCsvLine(lines.get(i), file.getFileName().toString());
//                        } catch (Exception e) {
//                            // NEVER allow scheduler to crash
//                            logger.error("Error processing line: {}", lines.get(i), e);
//                        }
//                    }
//
//                    moveToProcessed(file, processedPath);
//                }
//            }
//
//        } catch (Exception e) {
//            logger.error("Fatal scheduler error", e);
//        }
//    }
//
//    // ================= CSV LINE =================
//
//    private void processCsvLine(String line, String fileName) {
//
//        String[] parts = line.split(",");
//
//        if (parts.length < 4) {
//            logger.error("Invalid CSV format in file {} : {}", fileName, line);
//            return;
//        }
//
//        try {
//            long fromAccId = Long.parseLong(parts[0].trim());
//
//            Long toAccId = parts[1].trim().isEmpty()
//                    ? null
//                    : Long.parseLong(parts[1].trim());
//
//            TxnType txnType;
//            try {
//                txnType = TxnType.valueOf(parts[2].trim().toUpperCase());
//            } catch (IllegalArgumentException e) {
//                logger.error("Invalid transaction type: {}", parts[2]);
//                return;
//            }
//
//            BigDecimal amount = new BigDecimal(parts[3].trim());
//            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
//                logger.error("Invalid amount: {}", amount);
//                return;
//            }
//
//            processTransaction(fromAccId, toAccId, txnType, amount);
//
//        } catch (Exception e) {
//            logger.error("Bad record in file {} : {}", fileName, line, e);
//        }
//    }
//
//    // ================= TRANSACTION =================
//
//    @Transactional
//    public void processTransaction(
//            long fromAccId,
//            Long toAccId,
//            TxnType txnType,
//            BigDecimal amount
//    ) {
//
//        Account fromAccount = accountRepo.findById(fromAccId).orElse(null);
//        if (fromAccount == null) {
//            logger.error("From account {} not found", fromAccId);
//            return;
//        }
//
//        Account toAccount = null;
//        if (toAccId != null) {
//            toAccount = accountRepo.findById(toAccId).orElse(null);
//            if (toAccount == null) {
//                logger.error("To account {} not found", toAccId);
//                return;
//            }
//        }
//
//        switch (txnType) {
//            case DEPOSIT ->
//                    fromAccount.setBalance(fromAccount.getBalance().add(amount));
//
//            case WITHDRAW ->
//                    fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
//
//            case TRANSFER -> {
//                if (toAccount == null) {
//                    logger.error("Transfer requires destination account");
//                    return;
//                }
//                fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
//                toAccount.setBalance(toAccount.getBalance().add(amount));
//                accountRepo.save(toAccount);
//            }
//        }
//
//        accountRepo.save(fromAccount);
//
//        Transaction txn = new Transaction(
//                fromAccount,
//                toAccount,
//                txnType,
//                amount,
//                TxnStatus.SUCCESS,
//                new Timestamp(System.currentTimeMillis())
//        );
//
//        transactionRepo.save(txn);
//
//        logger.info(
//                "Transaction saved | From={} To={} | Type={} | Amount={}",
//                fromAccId,
//                toAccId,
//                txnType,
//                amount
//        );
//    }
//
//    // ================= FILE MOVE =================
//
//    private void moveToProcessed(Path file, Path processedPath) {
//        try {
//            Files.move(
//                    file,
//                    processedPath.resolve(file.getFileName()),
//                    StandardCopyOption.REPLACE_EXISTING
//            );
//            logger.info("File {} moved to processed folder", file.getFileName());
//        } catch (IOException e) {
//            logger.error("Failed to move file {}", file.getFileName(), e);
//        }
//    }
//}
