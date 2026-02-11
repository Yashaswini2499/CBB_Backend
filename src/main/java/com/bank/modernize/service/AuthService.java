package com.bank.modernize.service;

<<<<<<< HEAD
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
=======
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

>>>>>>> origin/main
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;

<<<<<<< HEAD
import com.bank.modernize.dto.LoginRequest;
import com.bank.modernize.dto.OtpRequest;
import com.bank.modernize.dto.RegisterRequest;
=======
import com.bank.modernize.dto.*;
>>>>>>> origin/main
import com.bank.modernize.entity.User;
import com.bank.modernize.enums.Role;
import com.bank.modernize.enums.Status;
import com.bank.modernize.repository.UserRepository;
import com.bank.modernize.security.JwtUtil;

@Service
<<<<<<< HEAD
=======
@RequiredArgsConstructor
>>>>>>> origin/main
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

<<<<<<< HEAD
    public AuthService(UserRepository repo, PasswordEncoder encoder, JwtUtil jwtUtil, EmailService emailService) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
    }

=======
>>>>>>> origin/main
    // ================= REGISTER =================
    public void register(RegisterRequest req) {

        if (repo.findByEmail(req.getEmail()).isPresent())
            throw new RuntimeException("Email already exists");

        User user = new User();
        user.setFullName(req.getFullName());
        user.setEmail(req.getEmail());
<<<<<<< HEAD
        user.setPhone(req.getPhone());
=======

        // 🔥 CLEAN PHONE (REMOVE +91, SPACES, SYMBOLS)
        String cleanPhone = req.getPhone().replaceAll("\\D", "");
        user.setPhone(cleanPhone);

>>>>>>> origin/main
        user.setPassword(encoder.encode(req.getPassword()));
        user.setRole(Role.CUSTOMER);
        user.setStatus(Status.ACTIVE);
        user.setMfaEnabled(false);

        repo.save(user);
    }

<<<<<<< HEAD
    // ================= LOGIN STEP =================
    public String login(LoginRequest req) {
=======
    // ================= LOGIN OTP (EMAIL) =================
    public String login(LoginRequest req) {

>>>>>>> origin/main
        User user = repo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

        if (!encoder.matches(req.getPassword(), user.getPassword()))
            throw new RuntimeException("INVALID_PASSWORD");

<<<<<<< HEAD
        // Generate OTP
=======
>>>>>>> origin/main
        String otp = String.valueOf(new SecureRandom().nextInt(900000) + 100000);

        user.setOtpCode(encoder.encode(otp));
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
        repo.save(user);

<<<<<<< HEAD
=======
        System.out.println("LOGIN OTP for " + user.getEmail() + " = " + otp);
>>>>>>> origin/main
        emailService.sendOtp(user.getEmail(), otp);

        return "OTP_SENT";
    }

<<<<<<< HEAD
    // ================= VERIFY OTP =================
=======
    // ================= VERIFY LOGIN OTP =================
>>>>>>> origin/main
    public String verifyOtp(OtpRequest req) {

        User user = repo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

<<<<<<< HEAD
        if (user.getOtpExpiry() == null || user.getOtpExpiry().isBefore(LocalDateTime.now()))
=======
        if (user.getOtpExpiry() == null ||
                user.getOtpExpiry().isBefore(LocalDateTime.now()))
>>>>>>> origin/main
            throw new RuntimeException("OTP_EXPIRED");

        if (!encoder.matches(req.getOtp(), user.getOtpCode()))
            throw new RuntimeException("INVALID_OTP");

        user.setOtpCode(null);
        user.setOtpExpiry(null);
        repo.save(user);

<<<<<<< HEAD
        // Return JWT with ROLE and USER ID
        return jwtUtil.generateToken(user.getEmail(), user.getRole().name(), user.getUserId());
    }

    // ================= FORGOT PASSWORD =================
=======
        return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
    }

    // ================= FORGOT PASSWORD (PHONE OTP) =================
    public String sendOtpToPhone(String phone) {

        // 🔥 CLEAN PHONE BEFORE SEARCH
        String cleanPhone = phone.replaceAll("\\D", "");

        User user = repo.findByPhone(cleanPhone)
                .orElseThrow(() -> new RuntimeException("PHONE_NOT_FOUND"));

        String otp = String.valueOf(new SecureRandom().nextInt(900000) + 100000);

        user.setResetOtp(encoder.encode(otp));
        user.setResetOtpExpiry(LocalDateTime.now().plusMinutes(5));
        repo.save(user);

        System.out.println("RESET PASSWORD OTP for " + cleanPhone + " = " + otp);

        return "OTP_SENT";
    }

    // ================= VERIFY RESET OTP =================
    public String verifyPhoneOtp(OtpRequest req) {

        String cleanPhone = req.getPhone().replaceAll("\\D", "");

        User user = repo.findByPhone(cleanPhone)
                .orElseThrow(() -> new RuntimeException("PHONE_NOT_FOUND"));

        if (user.getResetOtpExpiry() == null ||
                user.getResetOtpExpiry().isBefore(LocalDateTime.now()))
            throw new RuntimeException("OTP_EXPIRED");

        if (!encoder.matches(req.getOtp(), user.getResetOtp()))
            throw new RuntimeException("INVALID_OTP");

        return "OTP_VERIFIED";
    }

    // ================= RESET PASSWORD USING PHONE =================
    public String resetPasswordByPhone(String phone, String newPassword) {

        String cleanPhone = phone.replaceAll("\\D", "");

        User user = repo.findByPhone(cleanPhone)
                .orElseThrow(() -> new RuntimeException("PHONE_NOT_FOUND"));

        user.setPassword(encoder.encode(newPassword));
        user.setResetOtp(null);
        user.setResetOtpExpiry(null);

        repo.save(user);

        return "PASSWORD_RESET_SUCCESS";
    }

    // ================= EMAIL RESET TOKEN (OPTIONAL) =================
>>>>>>> origin/main
    public void forgotPassword(String email) {

        User user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

        String token = UUID.randomUUID().toString();

        user.setResetToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(15));
        repo.save(user);

<<<<<<< HEAD
        // Send reset link/token to email
        emailService.sendResetToken(email, token);

        System.out.println("Reset token for " + email + " = " + token); // DEBUG
    }

    // ================= RESET PASSWORD =================
=======
        emailService.sendResetToken(email, token);
    }

>>>>>>> origin/main
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
<<<<<<< HEAD
}
=======
}
>>>>>>> origin/main
