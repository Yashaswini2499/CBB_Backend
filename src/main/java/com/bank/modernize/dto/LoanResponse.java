package com.bank.modernize.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bank.modernize.enums.LoanStatus;
import com.bank.modernize.enums.LoanType;

import lombok.Data;

@Data
public class LoanResponse {
    private Long loanId;
    private Long customerId;
    private BigDecimal salary;
    private BigDecimal loanAmount;
    private Integer creditScore;
    private LoanType loanType;
    private Integer tenureMonths;
    private BigDecimal emi;
    private BigDecimal annualInterestRate; 
    private LoanStatus status;
    private LocalDateTime createdAt;
}
