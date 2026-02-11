package com.bank.modernize.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    // ================= SEND LOGIN OTP =================
    public void sendOtp(String email, String otp) {

        // Always print to console (for demo / fallback)
        System.out.println("OTP for " + email + " is: " + otp);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Your OTP Code");
            message.setText("Your OTP is: " + otp + "\nValid for 5 minutes.");

            mailSender.send(message);   // Try sending mail
            System.out.println("Email sent successfully to " + email);

        } catch (MailException ex) {
            // If email is fake / not reachable → do NOT fail app
            System.out.println("Email sending failed. Using console OTP only.");
        }
    }

    // ================= SEND RESET LINK =================
    public void sendResetToken(String email, String token) {

        System.out.println("Reset token for " + email + " is: " + token);

        String link = "http://localhost:8080/reset?token=" + token;

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Password Reset Request");
            message.setText("Click the link to reset password:\n" + link);

            mailSender.send(message);
            System.out.println("Reset link email sent to " + email);

        } catch (MailException ex) {
            System.out.println("Email sending failed. Using console reset link only.");
        }
    }
}