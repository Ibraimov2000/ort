package com.exam.ort.service.impl;

import com.exam.ort.mapper.UserMapper;
import com.exam.ort.model.UserRecord;
import com.exam.ort.entity.User;
import com.exam.ort.repository.UserRepository;
import com.exam.ort.service.UserService;
import com.exam.ort.exception.ResourceNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService, UserDetailsService {

    final UserRepository userRepository;
    final UserMapper userMapper;
    final PasswordEncoder passwordEncoder;


    @Override
    public UserRecord findById(long id) {
        return userMapper.toRecord(userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found")));
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserRecord> findAll() {
        return List.of();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole().name())));
    }

    @Override
    public UserRecord save(UserRecord userRecord) {
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

        return userMapper.toRecord(userRepository.save(user));
    }

    @Override
    public UserRecord getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow();
        return userMapper.toRecord(user);
    }
}
