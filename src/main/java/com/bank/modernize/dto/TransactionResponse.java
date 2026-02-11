package com.bank.modernize.dto;

import java.math.BigDecimal;

<<<<<<< HEAD
=======
import lombok.Data;

@Data
>>>>>>> origin/main
public class TransactionResponse {

    private Long transactionId;
    private String date;
    private String type;
    private Long account;
    private BigDecimal amount;
    private String status;

    public TransactionResponse(Long transactionId, String date, String type,
<<<<<<< HEAD
            Long account, BigDecimal amount, String status) {
=======
                               Long account, BigDecimal amount, String status) {
>>>>>>> origin/main
        this.transactionId = transactionId;
        this.date = date;
        this.type = type;
        this.account = account;
        this.amount = amount;
        this.status = status;
    }

<<<<<<< HEAD
    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
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
=======
>>>>>>> origin/main
}
