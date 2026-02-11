package com.bank.modernize.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.modernize.dto.ApiResponse;
import com.bank.modernize.dto.TransactionRequest;
import com.bank.modernize.dto.TransferRequest;
import com.bank.modernize.service.BankingService;

<<<<<<< HEAD
@RestController
@RequestMapping("/bank")
=======
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/bank")
@RequiredArgsConstructor
>>>>>>> origin/main
public class BankingController {

    private final BankingService service;

<<<<<<< HEAD
    public BankingController(BankingService service) {
        this.service = service;
    }

=======
>>>>>>> origin/main
    @PostMapping("/deposit")
    public ApiResponse deposit(@RequestBody TransactionRequest req) throws Exception {
        return service.deposit(req);
    }

    @PostMapping("/withdraw")
    public ApiResponse withdraw(@RequestBody TransactionRequest req) throws Exception {
        return service.withdraw(req);
    }

    @PostMapping("/transfer")
    public ApiResponse transfer(@RequestBody TransferRequest req) throws Exception {
        return service.transfer(req);
    }
<<<<<<< HEAD

    @GetMapping("/customer/{userId}/total-balance")
    public ResponseEntity<ApiResponse> total(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getCustomerTotalBalance(userId));
    }
=======
    
    @GetMapping("/my-total-balance")
    public ResponseEntity<ApiResponse> total(
            org.springframework.security.core.Authentication auth) {

        String email = auth.getName();
        return ResponseEntity.ok(service.getCustomerTotalBalanceByEmail(email));
    }

>>>>>>> origin/main
}
