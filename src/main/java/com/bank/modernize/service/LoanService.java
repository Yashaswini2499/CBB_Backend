package com.bank.modernize.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bank.modernize.adapter.CobolAdapter;
import com.bank.modernize.dto.LoanRequest;
import com.bank.modernize.dto.LoanResponse;
import com.bank.modernize.entity.Loan;
import com.bank.modernize.entity.User;
import com.bank.modernize.enums.LoanStatus;
import com.bank.modernize.enums.LoanType;
import com.bank.modernize.repository.LoanRepository;
import com.bank.modernize.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepo;
    private final CobolAdapter cobol;

    @Transactional
    public LoanResponse applyLoan(LoanRequest dto) {

        User user = userRepo.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Loan loan = Loan.builder()
                .customer(user)   
                .salary(dto.getSalary())
                .loanAmount(dto.getLoanAmount())
                .creditScore(dto.getCreditScore())
                .loanType(LoanType.valueOf(dto.getLoanType()))
                .tenureMonths(dto.getTenureMonths())
                .status(LoanStatus.PENDING)
                .build();

        loanRepository.save(loan);

        // 🔥 COBOL Calls
        double rate = cobol.calculateInterestRate(loan.getCreditScore());
        double emi = cobol.calculateEmi(
                loan.getLoanAmount().doubleValue(),
                rate,
                loan.getTenureMonths()
        );

        loan.setAnnualInterestRate(BigDecimal.valueOf(rate));
        loan.setEmi(BigDecimal.valueOf(emi));

        return mapToResponse(loanRepository.save(loan));
    }

    // Approve loan
    public LoanResponse approveLoan(Long loanId) {
        Loan loan = getLoanEntity(loanId);
        loan.setStatus(LoanStatus.APPROVED);
        return mapToResponse(loanRepository.save(loan));
    }

    // Reject loan
    public LoanResponse rejectLoan(Long loanId) {
        Loan loan = getLoanEntity(loanId);
        loan.setStatus(LoanStatus.REJECTED);
        return mapToResponse(loanRepository.save(loan));
    }

    // Get all loans of customer
    public List<LoanResponse> getLoansByCustomer(Long customerId) {
        return loanRepository.findByCustomerUserId(customerId) 
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get loan by ID
    public LoanResponse getLoanById(Long loanId) {
        return mapToResponse(getLoanEntity(loanId));
    }

    private Loan getLoanEntity(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found: " + loanId));
    }

    private LoanResponse mapToResponse(Loan loan) {
        LoanResponse res = new LoanResponse();
        res.setLoanId(loan.getLoanId());
        res.setCustomerId(loan.getCustomer().getUserId()); 
        res.setSalary(loan.getSalary());
        res.setLoanAmount(loan.getLoanAmount());
        res.setCreditScore(loan.getCreditScore());
        res.setLoanType(loan.getLoanType());
        res.setEmi(loan.getEmi());
        res.setAnnualInterestRate(loan.getAnnualInterestRate()); 
        res.setStatus(loan.getStatus());
        res.setCreatedAt(loan.getCreatedAt());
        return res;
    }
}
