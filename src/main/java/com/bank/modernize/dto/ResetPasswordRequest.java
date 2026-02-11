package com.bank.modernize.dto;

import lombok.Data;

@Data
public class ResetPasswordRequest {

    private String token;        // email reset token
    private String newPassword;

    // Optional (used if resetting by phone instead)
    private String phone;
}