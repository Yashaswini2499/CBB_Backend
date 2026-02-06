package com.bank.modernize.dto;
import lombok.*;

@Data
public class ResetPasswordRequest {
    private String token;
    private String newPassword;
}
