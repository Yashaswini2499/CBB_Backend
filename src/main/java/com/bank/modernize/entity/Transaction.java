package com.bank.modernize.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.bank.modernize.enums.TxnStatus;
import com.bank.modernize.enums.TxnType;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long txnId;

    @ManyToOne
    @JoinColumn(name = "from_acc_id", nullable = false)
    @NotNull
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_acc_id", nullable = true)
    private Account toAccount;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "txn_type", nullable = false)
    private TxnType txnType;

    @NotNull
    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TxnStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    // Hibernate needs no-args constructor
    public Transaction() {
    }

    // Convenience constructor
    public Transaction(Account fromAccount,
                       Account toAccount,
                       TxnType txnType,
                       BigDecimal amount,
                       TxnStatus status) {

        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.txnType = txnType;
        this.amount = amount;
        this.status = status;
    }

    // Auto timestamp
    @PrePersist
    protected void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}
