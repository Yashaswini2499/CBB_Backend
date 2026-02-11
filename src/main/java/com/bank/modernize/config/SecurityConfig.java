package com.bank.modernize.config;

<<<<<<< HEAD
=======
import lombok.RequiredArgsConstructor;
>>>>>>> origin/main
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;

import java.util.List;

import com.bank.modernize.security.JwtAuthFilter;

@Configuration
<<<<<<< HEAD
=======
@RequiredArgsConstructor
>>>>>>> origin/main
public class SecurityConfig {

    private final JwtAuthFilter jwtFilter;

<<<<<<< HEAD
    public SecurityConfig(JwtAuthFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

=======
    // 🔐 Password Encoder
>>>>>>> origin/main
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

<<<<<<< HEAD
=======
    // 🔐 Security Filter
>>>>>>> origin/main
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
<<<<<<< HEAD
                // Disable CSRF
                .csrf(csrf -> csrf.disable())

                // Enable CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Stateless session
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                        // Allow preflight
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Public login/signup APIs
                        .requestMatchers("/auth/**").permitAll()

                        // Secure everything else
                        .anyRequest().authenticated())

                // Add JWT filter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
=======
            // Disable CSRF
            .csrf(csrf -> csrf.disable())

            // Enable CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // Stateless session (JWT)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Authorization rules
            .authorizeHttpRequests(auth -> auth

                // Allow preflight requests
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // Public AUTH APIs
                .requestMatchers("/auth/**").permitAll()

                // Public APIs (for now — debugging)
                .requestMatchers(
                        "/accounts/**",
                        "/transactions/**",
                        "/users/**",
                        "/bank/**",
                        "/api/loans/**",
                        "/admin/dashboard/**"
                ).permitAll()

                // All other requests require authentication
                .anyRequest().authenticated()
            )

            // Add JWT filter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
>>>>>>> origin/main

        return http.build();
    }

<<<<<<< HEAD
=======
    // 🌐 CORS Configuration
>>>>>>> origin/main
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> origin/main
