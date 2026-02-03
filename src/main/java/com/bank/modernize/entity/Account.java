package com.bank.modernize.entity;

import jakarta.persistence.*;
import lombok.*;



@Entity
@Table(name = "accounts")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    @Column(nullable=false)
    private String accountType;
    
    @Column(nullable=false)
    private double balance;
    
    @Column(nullable=false)
    private String status;
}
