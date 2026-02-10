package com.bank.modernize.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;

import com.bank.modernize.dto.*;
import com.bank.modernize.entity.User;
import com.bank.modernize.enums.Role;
import com.bank.modernize.enums.Status;
import com.bank.modernize.repository.UserRepository;
import com.bank.modernize.security.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

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

    // ================= LOGIN (SEND OTP) =================
    public String login(LoginRequest req) {

        User user = repo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email"));

        if (!encoder.matches(req.getPassword(), user.getPassword()))
            throw new RuntimeException("Invalid password");

        // Generate OTP
        String otp = String.valueOf(new SecureRandom().nextInt(900000) + 100000);

        user.setOtpCode(encoder.encode(otp));
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
        repo.save(user);

        emailService.sendOtp(user.getEmail(), otp);

        return "OTP_SENT";
    }

    // ================= VERIFY OTP → RETURN TOKEN + ROLE =================
    public LoginResponse verifyOtp(OtpRequest req) {

        User user = repo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getOtpExpiry() == null || user.getOtpExpiry().isBefore(LocalDateTime.now()))
            throw new RuntimeException("OTP expired");

        if (!encoder.matches(req.getOtp(), user.getOtpCode()))
            throw new RuntimeException("Invalid OTP");

        user.setOtpCode(null);
        user.setOtpExpiry(null);

        repo.save(user);

        String token = jwtUtil.generateToken(user.getEmail());

        return new LoginResponse(
                token,
                user.getRole().name(),
                user.getEmail()
        );
    }

    // ================= FORGOT PASSWORD =================
    public void forgotPassword(String email) {

        User user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = UUID.randomUUID().toString();

        user.setResetToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(15));

        repo.save(user);

        emailService.sendResetLink(email, token);
    }

    // ================= RESET PASSWORD =================
    public void resetPassword(String token, String newPass) {

        User user = repo.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now()))
            throw new RuntimeException("Link expired");

        user.setPassword(encoder.encode(newPass));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);

        repo.save(user);
    }
}