package com.bank.modernize.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "borrowers")
@Data
public class Borrower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long borrowerId;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
