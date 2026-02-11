package com.bank.modernize.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bank.modernize.dto.ApplyLoanRequest;
import com.bank.modernize.dto.LoanResponse;
import com.bank.modernize.entity.Loan;
import com.bank.modernize.enums.LoanStatus;
import com.bank.modernize.repository.LoanRepository;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    // 1. Apply for a loan
    public LoanResponse applyLoan(ApplyLoanRequest request) {
        Loan loan = new Loan();
        loan.setCustomerId(request.getCustomerId());
        loan.setSalary(request.getSalary());
        loan.setLoanAmount(request.getLoanAmount());
        loan.setCreditScore(request.getCreditScore());
        loan.setLoanType(request.getLoanType());
        loan.setEmi(request.getEmi());
        loan.setStatus(LoanStatus.PENDING);
        loan.setCreatedAt(LocalDateTime.now());

        return mapToResponse(loanRepository.save(loan));
    }

    // 2. Approve loan
    public LoanResponse approveLoan(Long loanId) {
        Loan loan = getLoanEntity(loanId);
        loan.setStatus(LoanStatus.APPROVED);
        return mapToResponse(loanRepository.save(loan));
    }

    // 3. Reject loan
    public LoanResponse rejectLoan(Long loanId) {
        Loan loan = getLoanEntity(loanId);
        loan.setStatus(LoanStatus.REJECTED);
        return mapToResponse(loanRepository.save(loan));
    }

    // 4. Get all loans of customer
    public List<LoanResponse> getLoansByCustomer(Long customerId) {
        return loanRepository.findByCustomerId(customerId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 5. Get loan by ID
    public LoanResponse getLoanById(Long loanId) {
        return mapToResponse(getLoanEntity(loanId));
    }

    // Helpers
    private Loan getLoanEntity(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found: " + loanId));
    }

    private LoanResponse mapToResponse(Loan loan) {
        LoanResponse res = new LoanResponse();
        res.setLoanId(loan.getLoanId());
        res.setCustomerId(loan.getCustomerId());
        res.setSalary(loan.getSalary());
        res.setLoanAmount(loan.getLoanAmount());
        res.setCreditScore(loan.getCreditScore());
        res.setLoanType(loan.getLoanType());
        res.setEmi(loan.getEmi());
        res.setStatus(loan.getStatus());
        res.setCreatedAt(loan.getCreatedAt());
        return res;
    }
}
