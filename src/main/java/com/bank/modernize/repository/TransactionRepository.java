package com.bank.modernize.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bank.modernize.entity.*;
import com.bank.modernize.enums.TxnStatus;
import com.bank.modernize.enums.TxnType;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
        List<Transaction> findByFromAccount_AccountIdIn(List<Long> ids);
        List<Transaction> findByToAccount_AccountIdIn(List<Long> ids);
	    boolean existsByFromAccount_AccountId(Long accountId);
	    boolean existsByToAccount_AccountId(Long accountId);
	    long countByStatus(TxnStatus status);
	    List<Transaction> findByStatusAndTxnType(TxnStatus status, TxnType txnType);
	    List<Transaction> findAllByOrderByCreatedAtDesc();
}
