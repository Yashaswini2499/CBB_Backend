package com.bank.modernize.dto;
<<<<<<< HEAD

import com.bank.modernize.enums.Role;
import com.bank.modernize.enums.Status;

=======
import lombok.Data;
import com.bank.modernize.enums.Role;
import com.bank.modernize.enums.Status;

@Data
>>>>>>> origin/main
public class UserResponse {

    private Long userId;
    private String fullName;
    private String email;
    private String phone;
    private Role role;
    private Status status;
    private boolean mfaEnabled;
<<<<<<< HEAD

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isMfaEnabled() {
        return mfaEnabled;
    }

    public void setMfaEnabled(boolean mfaEnabled) {
        this.mfaEnabled = mfaEnabled;
    }
=======
>>>>>>> origin/main
}
