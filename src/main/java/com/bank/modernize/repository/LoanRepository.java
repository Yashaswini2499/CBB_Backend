package com.bank.modernize.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.modernize.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByCustomerId(Long customerId);
}
