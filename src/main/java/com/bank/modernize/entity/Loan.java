package com.bank.modernize.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.bank.modernize.enums.LoanStatus;
import com.bank.modernize.enums.LoanType;

@Entity
@Table(name = "loans")
@Data
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull
    private User customer;

    @NotNull
    private BigDecimal salary;

    @NotNull
    @Column(name = "loan_amount")
    private BigDecimal loanAmount;

    @Enumerated(EnumType.STRING)
    @NotNull
    private LoanType loanType;

    @NotNull
    private BigDecimal emi;

    @Enumerated(EnumType.STRING)
    @NotNull
    private LoanStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;
}
