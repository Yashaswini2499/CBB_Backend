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

    // ================= REGISTER =================
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        try {
            service.register(req);
            return ResponseEntity.ok("REGISTER_SUCCESS");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        try {
            return ResponseEntity.ok(service.login(req));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ================= VERIFY EMAIL OTP (LOGIN) =================
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest req) {
        try {
            return ResponseEntity.ok(service.verifyOtp(req));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ================= FORGOT PASSWORD (EMAIL RESET LINK) =================
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgot(@RequestBody ForgotPasswordRequest req) {
        try {
            service.forgotPassword(req.getEmail());
            return ResponseEntity.ok("RESET_LINK_SENT");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ================= FORGOT PASSWORD USING PHONE (SEND OTP) =================
    @PostMapping("/forgot-password-phone")
    public ResponseEntity<?> sendOtpPhone(@RequestBody ForgotPasswordRequest req) {
        try {
            return ResponseEntity.ok(service.sendOtpToPhone(req.getPhone()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ================= VERIFY PHONE OTP =================
    @PostMapping("/verify-phone-otp")
    public ResponseEntity<?> verifyPhoneOtp(@RequestBody OtpRequest req) {
        try {
            return ResponseEntity.ok(service.verifyPhoneOtp(req));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ================= RESET PASSWORD USING PHONE =================
    @PostMapping("/reset-password-phone")
    public ResponseEntity<?> resetPasswordPhone(@RequestBody ResetPasswordPhoneRequest req) {
        try {
            return ResponseEntity.ok(
                    service.resetPasswordByPhone(req.getPhone(), req.getNewPassword()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ================= RESET PASSWORD USING EMAIL TOKEN =================
    @PostMapping("/reset-password")
    public ResponseEntity<?> reset(@RequestBody ResetPasswordRequest req) {
        try {
            service.resetPassword(req.getToken(), req.getNewPassword());
            return ResponseEntity.ok("PASSWORD_RESET_SUCCESS");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ================= LOGOUT =================
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer "))
            return ResponseEntity.badRequest().body("TOKEN_MISSING");

        String token = header.substring(7);
        Date expiry = jwtUtil.extractExpiration(token);
        revokedTokenService.revokeToken(token, expiry.getTime());

        return ResponseEntity.ok("LOGOUT_SUCCESS");
    }
}