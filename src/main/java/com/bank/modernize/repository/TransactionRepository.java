package com.bank.modernize.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bank.modernize.entity.*;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
        List<Transaction> findByFromAccount_AccountIdIn(List<Long> ids);
        List<Transaction> findByToAccount_AccountIdIn(List<Long> ids);
	    boolean existsByFromAccount_AccountId(Long accountId);
	    boolean existsByToAccount_AccountId(Long accountId);
	
}
