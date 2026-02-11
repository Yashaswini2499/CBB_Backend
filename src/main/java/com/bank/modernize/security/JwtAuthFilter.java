package com.bank.modernize.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
<<<<<<< HEAD
=======
import lombok.RequiredArgsConstructor;
>>>>>>> origin/main
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
<<<<<<< HEAD
=======
@RequiredArgsConstructor
>>>>>>> origin/main
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final RevokedTokenService revokedTokenService;

<<<<<<< HEAD
    public JwtAuthFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService,
            RevokedTokenService revokedTokenService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.revokedTokenService = revokedTokenService;
    }

    /**
     * 🔴 IMPORTANT:
     * Skip JWT filter for /auth endpoints (login, register, verify-otp, forgot,
     * reset)
=======
    /**
     * 🔴 IMPORTANT:
     * Skip JWT filter for /auth endpoints (login, register, verify-otp, forgot, reset)
>>>>>>> origin/main
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/auth");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
<<<<<<< HEAD
            HttpServletResponse response,
            FilterChain chain)
=======
                                    HttpServletResponse response,
                                    FilterChain chain)
>>>>>>> origin/main
            throws ServletException, IOException {

        final String header = request.getHeader("Authorization");

        // If no token → continue (public request)
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        // If token revoked → block request
        if (revokedTokenService.isRevoked(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String email = jwtUtil.extractEmail(token);

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            if (jwtUtil.validateToken(token)) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

<<<<<<< HEAD
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
=======
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
>>>>>>> origin/main

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        chain.doFilter(request, response);
    }
}