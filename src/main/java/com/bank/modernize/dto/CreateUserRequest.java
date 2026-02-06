package com.bank.modernize.dto;
import lombok.Data;
import com.bank.modernize.enums.Role;
import com.bank.modernize.enums.Status;

@Data
public class CreateUserRequest {

    private String fullName;
    private String email;
    private String phone;
    private String password;
    private Role role;          // CUSTOMER or ADMIN
    private Status status;      // ACTIVE or INACTIVE
    private boolean mfaEnabled; // true or false
    private String mfaSecret;   // optional, can be null
}
