package com.exam.ort.service.impl;

import com.exam.ort.exception.*;
import com.exam.ort.mapper.UserMapper;
import com.exam.ort.model.UserDetailsImpl;
import com.exam.ort.model.UserRecord;
import com.exam.ort.entity.User;
import com.exam.ort.repository.TokenRepository;
import com.exam.ort.repository.UserRepository;
import com.exam.ort.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class UserServiceImpl implements UserService {

    public static final int MAX_FAILED_ATTEMPTS = 5;

    final UserRepository userRepository;
    final UserMapper userMapper;
    final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;


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
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loading user by username: {}", username);
        User user = userRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        return new UserDetailsImpl(user);}

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

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("no user with email: " + email + " found"));
    }

    @Override
    public User saveUser(User user) {
        String email = user.getEmail();

        if (emailExists(email)) {
            throw new EmailAlreadyExistsException();
        } else {

            String password = user.getPassword();
            user.setPassword(passwordEncoder.encode(password));

           /* Set<Role> roles = user.getRoles(); // get the roles that the user have provided

            user.setRoles(roles); // set the roles*/

            userRepository.save(user);
        }
        return user;
    }

    @Override
    public void updatePassword(String email, String password, String confirmPassword) {
        if (emailExists(email)) {
            User user = findUserByEmail(email);

            if (password.equals(confirmPassword)) {
                user.setPassword(passwordEncoder.encode(confirmPassword));
                userRepository.save(user);
            } else {
                throw new PasswordDontMatchException();
            }
        } else {
            throw new UserNotFoundException("no user with email: " + email + " found");
        }
    }

    @Override
    public User validateCredentials(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            user.setFailedAttempts(user.getFailedAttempts() + 1);

            userRepository.save(user);

            if (user.getFailedAttempts() >= MAX_FAILED_ATTEMPTS) {
                log.info("Locking account for user: {}", email);
                user.setAccountNonLocked(false);
                userRepository.save(user);
                throw new AccountLockedException();
            }

            throw new BadCredentialsException("Invalid credentials");
        }

        user.setFailedAttempts(0);
        userRepository.save(user);

        return user;
    }

    @Override
    public void enableUser(String email) {
        User user = findUserByEmail(email);
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void lockUser(String email) {
        User user = findUserByEmail(email);
        user.setAccountNonLocked(false);
        userRepository.save(user);
    }

    @Override
    public void unlockUser(String email) {
        User user = findUserByEmail(email);
        user.setAccountNonLocked(true);
        user.setFailedAttempts(0);
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAllUsers();
    }

    @Override
    public List<User> getLockedUsers() {
        return userRepository.findLockedUsers();
    }

    @Override
    public List<User> getUnlockedUsers() {
        return userRepository.findUnlockedUsers();
    }

    @Transactional
    @Override
    public void deleteUser(String email) {
        User user = findUserByEmail(email);
        long userId = user.getId();
        tokenRepository.deleteAllByUser(userId);
        userRepository.delete(user);
    }
}
