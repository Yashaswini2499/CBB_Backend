package com.bank.modernize.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "loans")
@Data
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(nullable=false)
    private double salary;
    
    @Column(nullable=false)
    private int riskScore;
    
    @Column(nullable=false)
    private double loanAmount;
    
    @Column(nullable=false)
    private double emi;
    
    @Column(nullable=false)
    private String status;
}
