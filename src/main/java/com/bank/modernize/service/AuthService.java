package com.bank.modernize.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;

import com.bank.modernize.dto.LoginRequest;
import com.bank.modernize.dto.OtpRequest;
import com.bank.modernize.dto.RegisterRequest;
import com.bank.modernize.entity.User;
import com.bank.modernize.enums.Role;
import com.bank.modernize.enums.Status;
import com.bank.modernize.repository.UserRepository;
import com.bank.modernize.security.JwtUtil;

@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    public AuthService(UserRepository repo, PasswordEncoder encoder, JwtUtil jwtUtil, EmailService emailService) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
    }

    // ================= REGISTER =================
    public void register(RegisterRequest req) {

        if (repo.findByEmail(req.getEmail()).isPresent())
            throw new RuntimeException("Email already exists");

        User user = new User();
        user.setFullName(req.getFullName());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setPassword(encoder.encode(req.getPassword()));
        user.setRole(Role.CUSTOMER);
        user.setStatus(Status.ACTIVE);
        user.setMfaEnabled(false);

        repo.save(user);
    }

    // ================= LOGIN STEP =================
    public String login(LoginRequest req) {
        User user = repo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

        if (!encoder.matches(req.getPassword(), user.getPassword()))
            throw new RuntimeException("INVALID_PASSWORD");

        // Generate OTP
        String otp = String.valueOf(new SecureRandom().nextInt(900000) + 100000);

        user.setOtpCode(encoder.encode(otp));
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
        repo.save(user);

        emailService.sendOtp(user.getEmail(), otp);

        return "OTP_SENT";
    }

    // ================= VERIFY OTP =================
    public String verifyOtp(OtpRequest req) {

        User user = repo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

        if (user.getOtpExpiry() == null || user.getOtpExpiry().isBefore(LocalDateTime.now()))
            throw new RuntimeException("OTP_EXPIRED");

        if (!encoder.matches(req.getOtp(), user.getOtpCode()))
            throw new RuntimeException("INVALID_OTP");

        user.setOtpCode(null);
        user.setOtpExpiry(null);
        repo.save(user);

        // Return JWT with ROLE and USER ID
        return jwtUtil.generateToken(user.getEmail(), user.getRole().name(), user.getUserId());
    }

    // ================= FORGOT PASSWORD =================
    public void forgotPassword(String email) {

        User user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

        String token = UUID.randomUUID().toString();

        user.setResetToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(15));
        repo.save(user);

        // Send reset link/token to email
        emailService.sendResetToken(email, token);

        System.out.println("Reset token for " + email + " = " + token); // DEBUG
    }

    // ================= RESET PASSWORD =================
    public void resetPassword(String token, String newPassword) {

        User user = repo.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("INVALID_TOKEN"));

        if (user.getResetTokenExpiry() == null ||
                user.getResetTokenExpiry().isBefore(LocalDateTime.now()))
            throw new RuntimeException("TOKEN_EXPIRED");

        user.setPassword(encoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);

        repo.save(user);
    }
}
