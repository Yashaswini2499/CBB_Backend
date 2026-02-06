package com.bank.modernize.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bank.modernize.dto.*;
import com.bank.modernize.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;   
import java.util.Date;
import com.bank.modernize.security.JwtUtil;       
import com.bank.modernize.security.RevokedTokenService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;
    private final JwtUtil jwtUtil;                   
    private final RevokedTokenService revokedTokenService; 

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        service.register(req);
        return ResponseEntity.ok("User Registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(service.login(req));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest req) {
        return ResponseEntity.ok(service.verifyOtp(req));
    }

    @PostMapping("/forgot-password")
    public void forgot(@RequestBody ForgotPasswordRequest req) {
        service.forgotPassword(req.getEmail());
    }

    @PostMapping("/reset-password")
    public void reset(@RequestBody ResetPasswordRequest req) {
        service.resetPassword(req.getToken(), req.getNewPassword());
    }
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer "))
            return ResponseEntity.badRequest().body("Token missing");

        String token = header.substring(7);

        Date expiry = jwtUtil.extractExpiration(token);

        revokedTokenService.revokeToken(token, expiry.getTime());

        return ResponseEntity.ok("Logged out successfully");
    }

}
