package com.bank.modernize.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.modernize.entity.Loan;
import com.bank.modernize.enums.LoanStatus;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByCustomerUserId(Long userId);
    
    List<Loan> findByStatus(LoanStatus status);
}


