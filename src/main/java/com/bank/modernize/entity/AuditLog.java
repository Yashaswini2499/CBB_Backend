package com.bank.modernize.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
<<<<<<< HEAD
=======
import lombok.*;

>>>>>>> origin/main
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "audit_logs")
<<<<<<< HEAD
=======
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
>>>>>>> origin/main
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @NotBlank
    @Column(nullable = false)
    private String action;

    @CreationTimestamp
    @Column(name = "timestamp", nullable = false, updatable = false)
<<<<<<< HEAD
    private LocalDateTime timestamp;

    public AuditLog() {
    }

    public AuditLog(Long logId, User user, String action, LocalDateTime timestamp) {
        this.logId = logId;
        this.user = user;
        this.action = action;
        this.timestamp = timestamp;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
=======
    private LocalDateTime timestamp; 
>>>>>>> origin/main
}
