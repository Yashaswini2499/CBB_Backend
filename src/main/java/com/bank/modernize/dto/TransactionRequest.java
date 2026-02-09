package com.bank.modernize.dto;

import lombok.Data;

@Data
public class TransactionRequest {
    private Long accountNumber;
    private double amount;
}

