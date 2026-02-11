package com.bank.modernize.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "mysecretkeymysecretkeymysecretkey12345";
    private final long EXPIRATION = 1000 * 60 * 60 * 24; // 24 hours

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // ================= GENERATE TOKEN =================
    public String generateToken(String email, String role, Long userId) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ================= EXTRACT EMAIL =================
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // ================= EXTRACT ROLE =================
    public String extractRole(String token) {
        return (String) getClaims(token).get("role");
    }

    // ================= EXTRACT USER ID =================
    public Long extractUserId(String token) {
        return getClaims(token).get("userId", Long.class);
    }

    // ================= EXTRACT EXPIRATION (FIX) =================
    public Date extractExpiration(String token) {
        return getClaims(token).getExpiration();
    }

    // ================= VALIDATE TOKEN =================
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ================= INTERNAL CLAIM PARSER =================
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}