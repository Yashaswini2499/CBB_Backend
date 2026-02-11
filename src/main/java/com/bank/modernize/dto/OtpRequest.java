package com.bank.modernize.dto;

<<<<<<< HEAD
public class OtpRequest {
    private String email;
    private String otp;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
=======
import lombok.Data;

@Data
public class OtpRequest {

    // For LOGIN OTP (Email)
    private String email;

    // For FORGOT PASSWORD OTP (Phone)
    private String phone;

    private String otp;
}
>>>>>>> origin/main
