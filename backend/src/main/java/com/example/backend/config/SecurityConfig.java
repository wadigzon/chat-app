package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // Disabling CSRF
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/register", "/api/login").permitAll()  // Allow unauthenticated access to these endpoints
                        .anyRequest().authenticated()  // Require authentication for all other endpoints
                )
                .formLogin(Customizer.withDefaults())  // Use default form login configuration
                .logout(Customizer.withDefaults());  // Use default logout configuration

        return http.build();
    }
}
