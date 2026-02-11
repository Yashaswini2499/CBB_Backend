package com.bank.modernize.dto;

import lombok.Data;

@Data
public class OtpRequest {

    // For LOGIN OTP (Email)
    private String email;

    // For FORGOT PASSWORD OTP (Phone)
    private String phone;

    private String otp;
}