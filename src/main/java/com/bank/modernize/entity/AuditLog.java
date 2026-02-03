package com.bank.modernize.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "audit_logs")
@Data
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;
    
    @Column(nullable=false)
    private Long userId;
    
    @Column(nullable=false)
    private String action;

}
