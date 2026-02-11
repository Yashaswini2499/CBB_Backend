package com.bank.modernize.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;   
import com.bank.modernize.enums.*;

public class AccountResponse {

    private Long accountId;
    private Long customerId;
    private Long accountNumber;
    private AccountType accountType;
    private BigDecimal balance;
    private AccountStatus status;
    private LocalDateTime createdAt;   // 👈 DB timestamp

    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public Long getAccountNumber() { return accountNumber; }
    public void setAccountNumber(Long accountNumber) { this.accountNumber = accountNumber; }

    public AccountType getAccountType() { return accountType; }
    public void setAccountType(AccountType accountType) { this.accountType = accountType; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public AccountStatus getStatus() { return status; }
    public void setStatus(AccountStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
