package com.bank.modernize.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TransactionResponse {

    private Long transactionId;
    private String date;
    private String type;
    private Long account;
    private BigDecimal amount;
    private String status;

    public TransactionResponse(Long transactionId, String date, String type,
                               Long account, BigDecimal amount, String status) {
        this.transactionId = transactionId;
        this.date = date;
        this.type = type;
        this.account = account;
        this.amount = amount;
        this.status = status;
    }

}
