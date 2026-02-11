package com.bank.modernize.service;

<<<<<<< HEAD
=======
import java.math.BigDecimal;
>>>>>>> origin/main
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

<<<<<<< HEAD
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
=======
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
>>>>>>> origin/main

        return mapToResponse(loanRepository.save(loan));
    }

<<<<<<< HEAD
    // 2. Approve loan
=======
    // Approve loan
>>>>>>> origin/main
    public LoanResponse approveLoan(Long loanId) {
        Loan loan = getLoanEntity(loanId);
        loan.setStatus(LoanStatus.APPROVED);
        return mapToResponse(loanRepository.save(loan));
    }

<<<<<<< HEAD
    // 3. Reject loan
=======
    // Reject loan
>>>>>>> origin/main
    public LoanResponse rejectLoan(Long loanId) {
        Loan loan = getLoanEntity(loanId);
        loan.setStatus(LoanStatus.REJECTED);
        return mapToResponse(loanRepository.save(loan));
    }

<<<<<<< HEAD
    // 4. Get all loans of customer
    public List<LoanResponse> getLoansByCustomer(Long customerId) {
        return loanRepository.findByCustomerId(customerId)
=======
    // Get all loans of customer
    public List<LoanResponse> getLoansByCustomer(Long customerId) {
        return loanRepository.findByCustomerUserId(customerId) 
>>>>>>> origin/main
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

<<<<<<< HEAD
    // 5. Get loan by ID
=======
    // Get loan by ID
>>>>>>> origin/main
    public LoanResponse getLoanById(Long loanId) {
        return mapToResponse(getLoanEntity(loanId));
    }

<<<<<<< HEAD
    // Helpers
=======
>>>>>>> origin/main
    private Loan getLoanEntity(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found: " + loanId));
    }

    private LoanResponse mapToResponse(Loan loan) {
        LoanResponse res = new LoanResponse();
        res.setLoanId(loan.getLoanId());
<<<<<<< HEAD
        res.setCustomerId(loan.getCustomerId());
=======
        res.setCustomerId(loan.getCustomer().getUserId()); 
>>>>>>> origin/main
        res.setSalary(loan.getSalary());
        res.setLoanAmount(loan.getLoanAmount());
        res.setCreditScore(loan.getCreditScore());
        res.setLoanType(loan.getLoanType());
        res.setEmi(loan.getEmi());
<<<<<<< HEAD
=======
        res.setAnnualInterestRate(loan.getAnnualInterestRate()); 
>>>>>>> origin/main
        res.setStatus(loan.getStatus());
        res.setCreatedAt(loan.getCreatedAt());
        return res;
    }
}
