package com.bank.modernize.dto;

import com.bank.modernize.enums.Status;
import lombok.Data;

@Data
public class UpdateUserRequest {

    private String fullName;
    private String phone;
    private Status status;        // ACTIVE / INACTIVE
    private Boolean mfaEnabled;   // true / false
    private String mfaSecret;     // optional
}
