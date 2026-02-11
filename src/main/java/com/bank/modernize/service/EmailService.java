package com.bank.modernize.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    // 🔴 TEMPORARY: Disable real email sending
    // Just print OTP in console

    public void sendOtp(String email, String otp) {
        System.out.println("=================================");
        System.out.println(" OTP for " + email + " = " + otp);
        System.out.println("=================================");
    }

    public void sendResetToken(String email, String token) {
        System.out.println("=================================");
        System.out.println(" RESET TOKEN for " + email + " = " + token);
        System.out.println("=================================");
    }
}