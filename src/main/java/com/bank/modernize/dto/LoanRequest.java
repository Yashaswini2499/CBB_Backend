package com.bank.modernize.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class LoanRequest {      

    private BigDecimal salary;      

    private BigDecimal loanAmount;  

    private Integer creditScore;    

    private Integer tenureMonths;  

    private String loanType;        
}

