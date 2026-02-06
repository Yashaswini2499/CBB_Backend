package com.bank.modernize.dto;
import lombok.*;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
