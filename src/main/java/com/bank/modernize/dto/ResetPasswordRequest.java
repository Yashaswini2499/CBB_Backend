package com.bank.modernize.dto;

<<<<<<< HEAD
public class ResetPasswordRequest {
    private String token;
    private String newPassword;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
=======
import lombok.Data;

@Data
public class ResetPasswordRequest {

    private String token;        // email reset token
    private String newPassword;

    // Optional (used if resetting by phone instead)
    private String phone;
}
>>>>>>> origin/main
