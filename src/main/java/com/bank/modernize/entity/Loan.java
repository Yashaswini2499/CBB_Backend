package com.bank.modernize.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bank.modernize.enums.LoanStatus;
import com.bank.modernize.enums.LoanType;

import jakarta.persistence.*;
<<<<<<< HEAD

@Entity
@Table(name = "loans")
=======
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Builder
@Entity
@Table(name = "loans")
@Data
>>>>>>> origin/main
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Long loanId;

<<<<<<< HEAD
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "salary", nullable = false, precision = 10, scale = 2)
    private BigDecimal salary;

    @Column(name = "loan_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal loanAmount;

    @Column(name = "credit_score", nullable = false)
    private Integer creditScore;

=======
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull
    private User customer;

    @Column(name = "salary", nullable = false, precision = 12, scale = 2)
    private BigDecimal salary;

    @Column(name = "credit_score", nullable = false)
    private Integer creditScore;

    @Column(name = "loan_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal loanAmount;

    @Column(name = "tenure_months", nullable = false)
    private Integer tenureMonths;  // REQUIRED for EMI

>>>>>>> origin/main
    @Enumerated(EnumType.STRING)
    @Column(name = "loan_type", nullable = false)
    private LoanType loanType;

<<<<<<< HEAD
    @Column(name = "emi", nullable = false, precision = 10, scale = 2)
    private BigDecimal emi;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LoanStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Loan() {
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public LoanType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    public BigDecimal getEmi() {
        return emi;
    }

    public void setEmi(BigDecimal emi) {
        this.emi = emi;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
=======
    @Column(name = "annual_interest_rate", precision = 5, scale = 2)
    private BigDecimal annualInterestRate;  

    @Column(name = "emi", precision = 12, scale = 2)
    private BigDecimal emi; 

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LoanStatus status = LoanStatus.PENDING;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
>>>>>>> origin/main
}
