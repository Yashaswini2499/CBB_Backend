package com.bank.modernize.dto;

import java.math.BigDecimal;
import com.bank.modernize.enums.AccountType;
import jakarta.validation.constraints.*;
<<<<<<< HEAD

=======
import lombok.Data;

@Data
>>>>>>> origin/main
public class CreateAccountRequest {

    @NotNull
    private Long customerId;

    @NotNull
    private AccountType accountType;

    @NotNull
    @DecimalMin(value = "500.0", message = "Minimum deposit is 500")
    private BigDecimal initialDeposit;
<<<<<<< HEAD

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getInitialDeposit() {
        return initialDeposit;
    }

    public void setInitialDeposit(BigDecimal initialDeposit) {
        this.initialDeposit = initialDeposit;
    }
=======
>>>>>>> origin/main
}
