package com.example.backend.config;

import com.example.backend.model.User;
import com.example.backend.security.JwtRequestFilter;
import com.example.backend.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.backend.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public SecurityConfig(@Lazy JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // Disabling CSRF
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/register", "/api/login").permitAll()  // Allow unauthenticated access to these endpoints
                        .anyRequest().authenticated()  // Require authentication for all other endpoints
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
                //.formLogin(Customizer.withDefaults())  // Use default form login configuration
                //.logout(Customizer.withDefaults());  // Use default logout configuration

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
