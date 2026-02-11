package com.bank.modernize.dto;

<<<<<<< HEAD
public class TransactionRequest {
    private Long accountNumber;
    private double amount;

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
=======
import lombok.Data;

@Data
public class TransactionRequest {
    private Long accountNumber;
    private double amount;
}

>>>>>>> origin/main
