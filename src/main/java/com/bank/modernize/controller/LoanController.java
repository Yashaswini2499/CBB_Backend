package com.bank.modernize.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bank.modernize.dto.ApplyLoanRequest;
import com.bank.modernize.dto.LoanResponse;
import com.bank.modernize.service.LoanService;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    // 1. Apply loan
    @PostMapping
    public ResponseEntity<LoanResponse> applyLoan(@RequestBody ApplyLoanRequest request) {
        return new ResponseEntity<>(loanService.applyLoan(request), HttpStatus.CREATED);
    }

    // 2. Approve loan
    @PutMapping("/{loanId}/approve")
    public ResponseEntity<LoanResponse> approveLoan(@PathVariable Long loanId) {
        return ResponseEntity.ok(loanService.approveLoan(loanId));
    }

    // 3. Reject loan
    @PutMapping("/{loanId}/reject")
    public ResponseEntity<LoanResponse> rejectLoan(@PathVariable Long loanId) {
        return ResponseEntity.ok(loanService.rejectLoan(loanId));
    }

    // 4. Get all loans of customer
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<LoanResponse>> getLoansByCustomer(
            @PathVariable Long customerId) {
        return ResponseEntity.ok(loanService.getLoansByCustomer(customerId));
    }

    // 5. Get loan by ID
    @GetMapping("/{loanId}")
    public ResponseEntity<LoanResponse> getLoanById(@PathVariable Long loanId) {
        return ResponseEntity.ok(loanService.getLoanById(loanId));
    }
}
