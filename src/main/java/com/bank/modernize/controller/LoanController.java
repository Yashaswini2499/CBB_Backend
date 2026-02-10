package com.bank.modernize.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bank.modernize.entity.Loan;
import com.bank.modernize.enums.LoanStatus;
import com.bank.modernize.enums.LoanType;
import com.bank.modernize.service.LoanService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping("/apply")
    public ResponseEntity<Loan> applyLoan(
            @RequestParam Long userId,
            @RequestParam BigDecimal salary,
            @RequestParam BigDecimal loanAmount,
            @RequestParam LoanType loanType) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(loanService.applyLoan(userId, salary, loanAmount, loanType));
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long loanId) {
        return ResponseEntity.ok(loanService.getLoanById(loanId));
    }

    @GetMapping("/customer/{userId}")
    public ResponseEntity<List<Loan>> getLoansByCustomerId(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                loanService.getLoansByCustomerId(userId)
        );
    }

    @PutMapping("/{loanId}/status")
    public ResponseEntity<Loan> approveOrRejectLoan(
            @PathVariable Long loanId,
            @RequestParam LoanStatus status) {

        return ResponseEntity.ok(
                loanService.approveOrRejectLoan(loanId, status)
        );
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Loan>> getLoansByStatus(
            @PathVariable LoanStatus status) {

        return ResponseEntity.ok(
                loanService.getLoansByStatus(status)
        );
    }
}