package com.exam.ort.config;

import com.exam.ort.entity.User;
import com.exam.ort.enums.Role;
import com.exam.ort.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    private User buildAdminUser() {
        return User.builder()
                .firstName("admin")
                .lastName("admin")
                .email(adminUsername)
                .password(adminPassword)
                .confirmPassword(adminPassword)
                .role(Role.ADMIN)
                .enabled(true)
                .accountNonLocked(true)
                .build();
    }

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.existsByEmail(adminUsername)) {
            log.info("Admin user already exists.\n username: {}", adminUsername);
            return;
        }

        User admin = buildAdminUser();
        admin.setPassword(passwordEncoder.encode(adminPassword));
        userRepository.save(admin);

        log.info("Admin user created successfully. username: {}", adminUsername);
    }
}