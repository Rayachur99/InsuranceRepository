package com.ct.usecase.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ct.usecase.demo.dto.LoginRequest;
import com.ct.usecase.demo.entity.UserEntity;
import com.ct.usecase.demo.exception.InvalidCredentialsException;
import com.ct.usecase.demo.repository.UserRepository;
import com.ct.usecase.demo.util.JwtUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        log.info("Login attempt for username={}", request.username());

        UserEntity user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> {
                    log.warn("Login failed - user not found for username={}", request.username());
                    return new InvalidCredentialsException("Invalid credentials");
                });

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            log.warn("Login failed - invalid password for username={}", request.username());
            throw new InvalidCredentialsException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getRole().name(),
                user.getOrganization() != null ? user.getOrganization().getId() : null
        );

        log.info("Login successful for username={}, role={}, orgId={}",
                user.getUsername(),
                user.getRole().name(),
                user.getOrganization() != null ? user.getOrganization().getId() : null
        );

        return token;
    }
}
