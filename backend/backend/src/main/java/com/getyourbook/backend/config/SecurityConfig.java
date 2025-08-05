package com.getyourbook.backend.config;

import com.getyourbook.backend.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Final, robust security configuration.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Explicitly use our custom CORS configuration source.
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                
                // 2. Disable CSRF protection for stateless APIs.
                .csrf(csrf -> csrf.disable())
                
                // 3. Configure authorization rules.
                .authorizeHttpRequests(authz -> authz
                        // Public endpoints that do not require authentication.
                        .requestMatchers("/api/users/register", "/api/users/login").permitAll()
                        // All other requests must be authenticated.
                        .anyRequest().authenticated()
                )
                
                // 4. Set session management to stateless.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 5. Add our custom JWT filter before the standard Spring Security filters.
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Defines the CORS configuration source bean.
     * This is the single source of truth for our CORS policy.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allow requests specifically from our frontend's origin.
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        // Allow all standard HTTP methods.
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Allow all headers to be sent.
        configuration.setAllowedHeaders(List.of("*"));
        // Allow credentials (like cookies or auth tokens) to be sent.
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Apply this CORS configuration to all routes in our application.
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
