package com.exam.ort.service.impl;

import com.exam.ort.config.JwtTokenProvider;
import com.exam.ort.entity.User;
import com.exam.ort.model.UserRecord;
import com.exam.ort.repository.UserRepository;
import com.exam.ort.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthServiceImpl implements AuthService {

    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;
    final AuthenticationManager authenticationManager;

        @Override
    public String register(UserRecord userRecord) {
        log.info("Registering user: {}", userRecord.email());

        if (userRepository.existsByEmail(userRecord.email())) {
            throw new IllegalArgumentException("Email already exists.");
        }

        String encodedPassword = passwordEncoder.encode(userRecord.password());

        User user = new User();
        user.setFirstName(userRecord.firstName());
        user.setLastName(userRecord.lastName());
        user.setEmail(userRecord.email());
        user.setPassword(encodedPassword);
        user.setRole(userRecord.role());

        userRepository.save(user);

        return JwtTokenProvider.generateToken(user.getEmail());
    }

    @Override
    public String login(String email, String password) {
        log.info("Logging in user: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getEmail(), password);
        authenticationManager.authenticate(authenticationToken);

        return JwtTokenProvider.generateToken(email);
    }

}
