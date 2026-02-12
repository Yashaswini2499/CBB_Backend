package com.bank.modernize.repository;

import com.bank.modernize.entity.EmiPayment;
import com.bank.modernize.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmiPaymentRepository extends JpaRepository<EmiPayment, Long> {

    List<EmiPayment> findByLoan(Loan loan);

    long countByLoan(Loan loan);
}
