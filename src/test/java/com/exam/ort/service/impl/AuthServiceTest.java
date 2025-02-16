package com.exam.ort.service.impl;

import com.exam.ort.config.JwtTokenProvider;
import com.exam.ort.entity.User;
import com.exam.ort.enums.Role;
import com.exam.ort.model.UserRecord;
import com.exam.ort.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void shouldAuthenticateAndReturnToken() {
        String email = "test@example.com";
        String password = "password";
        String hashedPassword = "hashed-password";
        String expectedToken = "mocked.jwt.token";

        User user = new User();
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setRole(Role.USER);

        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(email, password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(authRequest)).thenReturn(authRequest);

        try (MockedStatic<JwtTokenProvider> mockedTokenProvider = mockStatic(JwtTokenProvider.class)) {
            mockedTokenProvider.when(() -> JwtTokenProvider.generateToken(email)).thenReturn(expectedToken);

            String actualToken = authService.login(email, password);

            assertEquals(expectedToken, actualToken);
            verify(authenticationManager, times(1)).authenticate(authRequest);
            mockedTokenProvider.verify(() -> JwtTokenProvider.generateToken(email), times(1));
        }
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        String email = "notfound@example.com";
        String password = "password";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authService.login(email, password));
    }

    @Test
    void shouldRegisterUserAndReturnToken() {
        String email = "newuser@example.com";
        String password = "securePassword";
        String hashedPassword = "hashed-securePassword";
        String expectedToken = "mocked.jwt.token";

        UserRecord userRecord = new UserRecord(1L, "John", "Doe", email, Role.USER, password);

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn(hashedPassword);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        try (MockedStatic<JwtTokenProvider> mockedTokenProvider = mockStatic(JwtTokenProvider.class)) {
            mockedTokenProvider.when(() -> JwtTokenProvider.generateToken(email)).thenReturn(expectedToken);

            String actualToken = authService.register(userRecord);

            assertEquals(expectedToken, actualToken);
            verify(userRepository, times(1)).save(any(User.class));
            mockedTokenProvider.verify(() -> JwtTokenProvider.generateToken(email), times(1));
        }
    }

}

