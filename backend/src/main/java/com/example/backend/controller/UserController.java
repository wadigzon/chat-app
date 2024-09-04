package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public Map<String, Object> registerUser(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();

        // Check if username already exists
        if (userRepository.findByUsername(user.getUsername()) != null) {
            response.put("message", "Username already exists");
            return response;
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        response.put("message", "User registered successfully");
        return response;
    }

    @PostMapping("/login")
    public Map<String, Object> loginUser(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        User foundUser = userRepository.findByUsername(user.getUsername());

        if (foundUser != null && bCryptPasswordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            // Generate JWT token
            String token = jwtTokenUtil.generateToken(foundUser.getUsername());
            response.put("message", "Login successful");
            response.put("user", foundUser.getUsername());
            response.put("token", token); // Return the token to the client
        } else {
            response.put("message", "Invalid username or password");
        }
        return response;
    }
}
