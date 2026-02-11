package com.bank.modernize.dto;

import lombok.Data;

@Data
public class ResetPasswordPhoneRequest {
    private String phone;
    private String newPassword;
}