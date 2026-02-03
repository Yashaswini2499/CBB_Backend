package com.bank.modernize.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(nullable=false)
    private String fullName;

    @Column(unique = true,nullable=false)
    private String email;

    private String phone;
    
    @Column(nullable=false)
    private String password;
    
    @Column(nullable=false)
    private String role;
    
    @Column(nullable=false)
    private String status;

    @Column(nullable=false)
    private LocalDateTime createdAt = LocalDateTime.now();
}