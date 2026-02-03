package com.bank.modernize.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "admins")
@Data
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @Column(nullable=false)
    private String fullName;

    @Column(unique = true,nullable=false)
    private String email;

    @Column(nullable=false)
    private String password;
    
    @Column(nullable=false)
    private String role;
}
