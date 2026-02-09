package com.bank.modernize.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.modernize.dto.TransactionResponse;
import com.bank.modernize.service.TransactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
	
	private final TransactionService transactionService;
	
	@GetMapping("/customer/{userId}")
	public List<TransactionResponse> history(@PathVariable Long userId) {
	    return transactionService.getCustomerTransactions(userId);
	}

}
