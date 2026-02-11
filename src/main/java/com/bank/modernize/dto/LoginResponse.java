package com.bank.modernize.dto;

<<<<<<< HEAD
=======
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
>>>>>>> origin/main
public class LoginResponse {

    private String token;
    private String role;
    private String email;
<<<<<<< HEAD

    public LoginResponse() {
    }

    public LoginResponse(String token, String role, String email) {
        this.token = token;
        this.role = role;
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
=======
>>>>>>> origin/main
}