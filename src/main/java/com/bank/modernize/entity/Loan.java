package com.bank.modernize.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.bank.modernize.enums.LoanStatus;
import com.bank.modernize.enums.LoanType;
import org.hibernate.annotations.CreationTimestamp;

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
    @Column(precision = 12, scale = 2)
    private BigDecimal salary;

    @NotNull
    @Column(name = "loan_amount", precision = 15, scale = 2)
    private BigDecimal loanAmount;

    // 🔹 CREDIT SCORE
    @Min(300)
    @Max(900)
    @Column(name = "credit_score", nullable = false)
    private Integer creditScore;

    @Enumerated(EnumType.STRING)
    @NotNull
    private LoanType loanType;

    @NotNull
    @Column(precision = 12, scale = 2)
    private BigDecimal emi;

    @Enumerated(EnumType.STRING)
    @NotNull
    private LoanStatus status = LoanStatus.PENDING;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;
}
