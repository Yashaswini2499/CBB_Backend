package com.bank.modernize.dto;

import java.math.BigDecimal;

import com.bank.modernize.enums.LoanType;

import lombok.Data;

@Data
public class ApplyLoanRequest {

    private Long customerId;
    private BigDecimal salary;
    private BigDecimal loanAmount;
    private Integer creditScore;
    private LoanType loanType;
    private BigDecimal emi;
}
