package com.bank.modernize.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bank.modernize.entity.*;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	       

	    boolean existsByFromAccount_AccountId(Long accountId);

	    boolean existsByToAccount_AccountId(Long accountId);
	
}
