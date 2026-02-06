package com.bank.modernize.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bank.modernize.dto.AccountResponse;
import com.bank.modernize.dto.CreateAccountRequest;
import com.bank.modernize.service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    // CREATE
    @PostMapping("/create")
    public ResponseEntity<AccountResponse> createAccount(
            @RequestBody CreateAccountRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(accountService.createAccount(request));
    }

    // GET BY ID
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccountById(
            @PathVariable Long accountId) {

        return ResponseEntity.ok(
                accountService.getAccountById(accountId));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {

        return ResponseEntity.ok(
                accountService.getAllAccounts());
    }

    // GET BY CUSTOMER
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<AccountResponse>> getByCustomerId(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                accountService.getAccountsByCustomerId(customerId));
    }

    // DELETE BY ACCOUNT ID
    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> deleteByAccountId(
            @PathVariable Long accountId) {

        accountService.deleteAccountById(accountId);

        return ResponseEntity.ok("Account deleted successfully");
    }

    // DELETE BY CUSTOMER ID
    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<String> deleteByCustomerId(
            @PathVariable Long customerId) {

        accountService.deleteAccountsByCustomerId(customerId);

        return ResponseEntity.ok(
                "All accounts of customer deleted successfully");
    }
}
