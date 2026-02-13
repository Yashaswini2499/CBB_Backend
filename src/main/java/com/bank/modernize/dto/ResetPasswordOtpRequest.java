package com.bank.modernize.dto;

import lombok.Data;

@Data
public class ResetPasswordOtpRequest {
    private String email;
    private String otp;
    private String newPassword;
}