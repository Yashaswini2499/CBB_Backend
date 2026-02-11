package com.bank.modernize.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bank.modernize.dto.AccountResponse;
import com.bank.modernize.dto.CreateAccountRequest;
import com.bank.modernize.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

        private final AccountService accountService;

        public AccountController(AccountService accountService) {
                this.accountService = accountService;
        }

        // =========================
        // CREATE ACCOUNT (LOGGED-IN USER)
        // =========================
        @PostMapping("/create")
        public ResponseEntity<AccountResponse> createAccount(
                        @RequestBody CreateAccountRequest request,
                        org.springframework.security.core.Authentication auth) {

                String email = auth.getName(); // email from JWT

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(accountService.createAccountForLoggedInUser(request, email));
        }

        // =========================
        // GET ACCOUNT BY ID
        // =========================
        @GetMapping("/{accountId}")
        public ResponseEntity<AccountResponse> getAccountById(
                        @PathVariable Long accountId) {

                return ResponseEntity.ok(
                                accountService.getAccountById(accountId));
        }

        // =========================
        // GET ALL ACCOUNTS
        // =========================
        @GetMapping
        public ResponseEntity<List<AccountResponse>> getAllAccounts() {

                return ResponseEntity.ok(
                                accountService.getAllAccounts());
        }

        // =========================
        // GET ACCOUNTS BY CUSTOMER ID
        // =========================
        @GetMapping("/customer/{customerId}")
        public ResponseEntity<List<AccountResponse>> getByCustomerId(
                        @PathVariable Long customerId) {

                return ResponseEntity.ok(
                                accountService.getAccountsByCustomerId(customerId));
        }

        // =========================
        // GET LOGGED-IN USER ACCOUNTS
        // =========================
        @GetMapping("/my-accounts")
        public ResponseEntity<List<AccountResponse>> getMyAccounts(
                        org.springframework.security.core.Authentication auth) {

                String email = auth.getName();

                return ResponseEntity.ok(
                                accountService.getAccountsByEmail(email));
        }

        // =========================
        // DELETE ACCOUNT
        // =========================
        @DeleteMapping("/{accountId}")
        public ResponseEntity<String> deleteByAccountId(
                        @PathVariable Long accountId) {

                accountService.deleteAccountById(accountId);

                return ResponseEntity.ok("Account deleted successfully");
        }

        @DeleteMapping("/customer/{customerId}")
        public ResponseEntity<String> deleteByCustomerId(
                        @PathVariable Long customerId) {

                accountService.deleteAccountsByCustomerId(customerId);

                return ResponseEntity.ok(
                                "All accounts of customer deleted successfully");
        }
}