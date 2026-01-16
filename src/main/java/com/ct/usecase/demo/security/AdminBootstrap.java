package com.ct.usecase.demo.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ct.usecase.demo.entity.UserEntity;
import com.ct.usecase.demo.repository.UserRepository;
import com.ct.usecase.demo.util.UserRole;

@Component
public class AdminBootstrap implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminBootstrap(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("admin").isEmpty()) {

            UserEntity admin = new UserEntity();
            admin.setUsername("admin");
            admin.setPasswordHash(passwordEncoder.encode("admin123"));
            admin.setRole(UserRole.SYSTEM_ADMIN);
            admin.setOrganization(null);

            userRepository.save(admin);
        }
    }
}

