package com.bank.modernize.service;

import com.bank.modernize.adapter.CobolAdapter;
import com.bank.modernize.dto.*;
import com.bank.modernize.entity.*;
import com.bank.modernize.enums.*;
import com.bank.modernize.repository.*;
import jakarta.transaction.Transactional;
<<<<<<< HEAD

=======
import lombok.RequiredArgsConstructor;
>>>>>>> origin/main
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
<<<<<<< HEAD
=======
@RequiredArgsConstructor
>>>>>>> origin/main
public class BankingService {

    private final AccountRepository repo;
    private final CobolAdapter cobol;
    private final TxnLogService txnLogService;
<<<<<<< HEAD

    public BankingService(AccountRepository repo, CobolAdapter cobol, TxnLogService txnLogService) {
        this.repo = repo;
        this.cobol = cobol;
        this.txnLogService = txnLogService;
    }
=======
    private final UserRepository userRepo;


>>>>>>> origin/main

    @Transactional
    public ApiResponse deposit(TransactionRequest req) {

        Account acc = repo.findByAccountNumber(req.getAccountNumber())
                .orElseThrow(() -> new RuntimeException("Account not found"));

<<<<<<< HEAD
        Transaction txn = new Transaction();
        txn.setFromAccount(acc);
        txn.setTxnType(TxnType.DEPOSIT);
        txn.setAmount(BigDecimal.valueOf(req.getAmount()));
        txn.setStatus(TxnStatus.PENDING);
=======
        Transaction txn = Transaction.builder()
                .fromAccount(acc)
                .txnType(TxnType.DEPOSIT)
                .amount(BigDecimal.valueOf(req.getAmount()))
                .status(TxnStatus.PENDING)
                .build();
>>>>>>> origin/main

        txn = txnLogService.save(txn);

        try {
            double newBal = cobol.calculateDeposit(
                    acc.getBalance().doubleValue(),
<<<<<<< HEAD
                    req.getAmount());
=======
                    req.getAmount()
            );
>>>>>>> origin/main

            acc.setBalance(BigDecimal.valueOf(newBal));
            repo.save(acc);

            txn.setStatus(TxnStatus.SUCCESS);
            txnLogService.save(txn);

            return new ApiResponse("SUCCESS", "Deposit Completed", acc.getBalance());

        } catch (Exception e) {
            txn.setStatus(TxnStatus.FAILED);
            txnLogService.save(txn);
            throw e;
        }
    }

<<<<<<< HEAD
=======

>>>>>>> origin/main
    @Transactional
    public ApiResponse withdraw(TransactionRequest req) {

        Account acc = repo.findByAccountNumber(req.getAccountNumber())
                .orElseThrow(() -> new RuntimeException("Account not found"));

<<<<<<< HEAD
        Transaction txn = new Transaction();
        txn.setFromAccount(acc);
        txn.setTxnType(TxnType.WITHDRAW);
        txn.setAmount(BigDecimal.valueOf(req.getAmount()));
        txn.setStatus(TxnStatus.PENDING);
=======
        Transaction txn = Transaction.builder()
                .fromAccount(acc)
                .txnType(TxnType.WITHDRAW)
                .amount(BigDecimal.valueOf(req.getAmount()))
                .status(TxnStatus.PENDING)
                .build();
>>>>>>> origin/main

        txn = txnLogService.save(txn);

        try {
            double newBal = cobol.calculateWithdraw(
                    acc.getBalance().doubleValue(),
<<<<<<< HEAD
                    req.getAmount());
=======
                    req.getAmount()
            );
>>>>>>> origin/main

            acc.setBalance(BigDecimal.valueOf(newBal));
            repo.save(acc);

            txn.setStatus(TxnStatus.SUCCESS);
            txnLogService.save(txn);

            return new ApiResponse("SUCCESS", "Withdrawal Completed", acc.getBalance());

        } catch (Exception e) {
            txn.setStatus(TxnStatus.FAILED);
            txnLogService.save(txn);
            throw e;
        }
    }

<<<<<<< HEAD
=======

>>>>>>> origin/main
    @Transactional
    public ApiResponse transfer(TransferRequest req) {

        Account from = repo.findByAccountNumber(req.getFromAccountNumber())
                .orElseThrow(() -> new RuntimeException("From account not found"));

        Account to = repo.findByAccountNumber(req.getToAccountNumber())
                .orElseThrow(() -> new RuntimeException("To account not found"));

<<<<<<< HEAD
        Transaction txn = new Transaction();
        txn.setFromAccount(from);
        txn.setToAccount(to);
        txn.setTxnType(TxnType.TRANSFER);
        txn.setAmount(BigDecimal.valueOf(req.getAmount()));
        txn.setStatus(TxnStatus.PENDING);
=======
        Transaction txn = Transaction.builder()
                .fromAccount(from)
                .toAccount(to)
                .txnType(TxnType.TRANSFER)
                .amount(BigDecimal.valueOf(req.getAmount()))
                .status(TxnStatus.PENDING)
                .build();
>>>>>>> origin/main

        txn = txnLogService.save(txn);

        try {
            double[] res = cobol.calculateTransfer(
                    from.getBalance().doubleValue(),
                    to.getBalance().doubleValue(),
<<<<<<< HEAD
                    req.getAmount());
=======
                    req.getAmount()
            );
>>>>>>> origin/main

            from.setBalance(BigDecimal.valueOf(res[0]));
            to.setBalance(BigDecimal.valueOf(res[1]));

            repo.save(from);
            repo.save(to);

            txn.setStatus(TxnStatus.SUCCESS);
            txnLogService.save(txn);

            return new ApiResponse("SUCCESS", "Transfer Completed", null);

        } catch (Exception e) {
            txn.setStatus(TxnStatus.FAILED);
            txnLogService.save(txn);
            throw e;
        }
    }
<<<<<<< HEAD

=======
    
    @Transactional
    public ApiResponse getCustomerTotalBalanceByEmail(String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return getCustomerTotalBalance(user.getUserId());
    }
    
>>>>>>> origin/main
    @Transactional
    public ApiResponse getCustomerTotalBalance(Long userId) {

        List<Account> accounts = repo.findByCustomerUserId(userId);

        if (accounts.isEmpty())
            throw new RuntimeException("Customer has no accounts");

        List<Long> balancesInCents = new ArrayList<>();

        for (Account acc : accounts) {
<<<<<<< HEAD
            BigDecimal cents = acc.getBalance().movePointRight(2);
=======
            BigDecimal cents = acc.getBalance().movePointRight(2); 
>>>>>>> origin/main
            balancesInCents.add(cents.longValueExact());
        }

        double total = cobol.calculateTotalBalance(balancesInCents);

        return new ApiResponse("SUCCESS", "Total balance calculated", total);
    }

<<<<<<< HEAD
=======

>>>>>>> origin/main
}
