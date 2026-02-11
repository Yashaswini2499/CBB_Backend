package com.bank.modernize.dto;

<<<<<<< HEAD
public class ForgotPasswordRequest {
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
=======
import lombok.Data;

@Data
public class ForgotPasswordRequest {

    private String email;   // used for email reset
    private String phone;   // used for phone OTP reset
}
>>>>>>> origin/main
