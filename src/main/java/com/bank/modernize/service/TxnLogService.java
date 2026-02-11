package com.bank.modernize.service;

import com.bank.modernize.entity.Transaction;
import com.bank.modernize.repository.TransactionRepository;
import jakarta.transaction.Transactional;
<<<<<<< HEAD
import org.springframework.stereotype.Service;

@Service
=======
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
>>>>>>> origin/main
public class TxnLogService {

    private final TransactionRepository txnRepo;

<<<<<<< HEAD
    public TxnLogService(TransactionRepository txnRepo) {
        this.txnRepo = txnRepo;
    }

=======
>>>>>>> origin/main
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Transaction save(Transaction txn) {
        return txnRepo.save(txn);
    }
}
