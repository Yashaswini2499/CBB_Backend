package com.bank.modernize.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bank.modernize.entity.Loan;
import com.bank.modernize.enums.LoanStatus;

public interface LoanRepository extends JpaRepository<Loan, Long> {

<<<<<<< HEAD
	List<Loan> findByCustomerUserId(Long userId);
=======
    List<Loan> findByCustomerId(Long customerId);

>>>>>>> login-integration
    long countByStatus(LoanStatus status);
}