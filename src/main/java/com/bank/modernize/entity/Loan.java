package com.bank.modernize.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.bank.modernize.enums.LoanStatus;
import com.bank.modernize.enums.LoanType;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "loans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull
    private User customer;

    @NotNull
    @DecimalMin(value = "1.0", message = "Salary must be positive")
    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal salary;

    @NotNull
    @DecimalMin(value = "1000.0", message = "Loan amount too small")
    @Column(name = "loan_amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal loanAmount;

    @Min(300)
    @Max(900)
    @Column(name = "credit_score", nullable = false)
    private Integer creditScore;

    @Enumerated(EnumType.STRING)
    @NotNull
    private LoanType loanType;

    @NotNull
    @DecimalMin(value = "1.0", message = "EMI must be positive")
    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal emi;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private LoanStatus status = LoanStatus.PENDING;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.status = LoanStatus.PENDING;
    }


}



