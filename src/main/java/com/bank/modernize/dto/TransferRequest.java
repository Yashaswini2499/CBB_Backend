package com.bank.modernize.dto;

import lombok.Data;

@Data
public class TransferRequest {
    private Long fromAccountNumber;
    private Long toAccountNumber;
    private double amount;
}
