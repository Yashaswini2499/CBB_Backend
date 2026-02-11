package com.bank.modernize.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.bank.modernize.entity.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByAccountNumber(Long accountNumber);
    
    List<Account> findByCustomerUserId(Long userId);

    void deleteByCustomerUserId(Long userId);

	boolean existsById(Long accountId);
	
	Optional<Account> findByAccountNumber(Long accountNumber);

}
