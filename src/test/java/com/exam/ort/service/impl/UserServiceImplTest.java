package com.exam.ort.service.impl;

import com.exam.ort.entity.User;
import com.exam.ort.enums.Role;
import com.exam.ort.repository.UserRepository;
import com.exam.ort.model.UserRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void saveUser() {
        UserRecord userRecord = new UserRecord(1L, "John", "Doe", "john.doe@example.com", Role.ADMIN, "password");
        User user = new User(1L, "John", "Doe", "john.doe@example.com", Role.ADMIN, "password");

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(any(String.class))).thenReturn("password");

        UserRecord result = userService.save(userRecord);

        assertNotNull(result);
        assertEquals("John", result.firstName());
        assertEquals("Doe", result.lastName());

        verify(userRepository, times(1)).save(any(User.class));
    }
}
