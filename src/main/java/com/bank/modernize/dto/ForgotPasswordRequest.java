package com.bank.modernize.dto;

import lombok.Data;

@Data
public class ForgotPasswordRequest {

    private String email;   // used for email reset
    private String phone;   // used for phone OTP reset
}