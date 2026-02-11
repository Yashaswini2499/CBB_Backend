package com.bank.modernize.dto;
import lombok.*;

@Data
public class OtpRequest {
    private String email;
    private String otp;
}
