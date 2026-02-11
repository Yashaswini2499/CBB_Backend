package com.bank.modernize.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.modernize.entity.Loan;
import com.bank.modernize.entity.User;
import com.bank.modernize.enums.LoanStatus;
import com.bank.modernize.enums.LoanType;
import com.bank.modernize.repository.LoanRepository;
import com.bank.modernize.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepo;
    private final UserRepository userRepo;

    @Transactional
    public Loan applyLoan(
            Long userId,
            BigDecimal salary,
            BigDecimal loanAmount,
            LoanType loanType) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Eligibility rule: max 10x salary
        BigDecimal maxEligibleAmount = salary.multiply(BigDecimal.TEN);

        if (loanAmount.compareTo(maxEligibleAmount) > 0) {
            throw new RuntimeException("Loan amount exceeds salary eligibility");
        }

        Loan loan = new Loan();
        loan.setCustomer(user);
        loan.setSalary(salary);
        loan.setLoanAmount(loanAmount);
        loan.setLoanType(loanType);
        loan.setEmi(loanAmount.divide(BigDecimal.valueOf(12), 2));
        loan.setStatus(LoanStatus.APPLIED);
        loan.setCreatedAt(LocalDateTime.now());

        return loanRepo.save(loan);
    }

    public Loan getLoanById(Long loanId) {
        return loanRepo.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
    }

    public List<Loan> getLoansByCustomerId(Long userId) {
        return loanRepo.findByCustomerUserId(userId);
    }

    @Transactional
    public Loan approveOrRejectLoan(Long loanId, LoanStatus status) {

        Loan loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (loan.getStatus() != LoanStatus.APPLIED) {
            throw new RuntimeException("Only APPLIED loans can be approved or rejected");
        }

        loan.setStatus(status);
        return loanRepo.save(loan);
    }

    public List<Loan> getLoansByStatus(LoanStatus status) {
        return loanRepo.findByStatus(status);
    }
}