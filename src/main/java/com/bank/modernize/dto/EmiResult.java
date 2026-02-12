package com.bank.modernize.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmiResult {
    private double emi;
    private double totalPayment;
    private double totalInterest;
}
