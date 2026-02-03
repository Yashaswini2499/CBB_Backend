package com.bank.modernize.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long txnId;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(nullable=false)
    private String txnType;
    
    @Column(nullable=false)
    private double amount;
    
    @Column(nullable=false)
    private String targetAccount;
    
    @Column(nullable=false)
    private String status;
}
