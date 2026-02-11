package com.bank.modernize.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionHistoryResponse {

    private Long transactionId;
    private LocalDateTime date;
    private String userName;
    private String type;
    private Long accountNumber;
    private BigDecimal amount;
    private String status;

    public TransactionHistoryResponse(Long transactionId, LocalDateTime date, String userName, String type,
            Long accountNumber, BigDecimal amount, String status) {
        this.transactionId = transactionId;
        this.date = date;
        this.userName = userName;
        this.type = type;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.status = status;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
