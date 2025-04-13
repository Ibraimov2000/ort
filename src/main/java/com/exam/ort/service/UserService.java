package com.exam.ort.service;

import com.exam.ort.entity.User;
import com.exam.ort.model.UserDetailsImpl;
import com.exam.ort.model.UserRecord;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    void deleteById(long id);
    List<UserRecord> findAll();
    UserRecord findById(long id);
    UserDetailsImpl loadUserByUsername(String username);

    UserRecord save(UserRecord userRecord);

    UserRecord getCurrentUser();

    boolean emailExists(String email);
    User findUserByEmail(String email);
    User saveUser(User user);
    void updatePassword(String email, String password, String confirmPassword);
    User validateCredentials(String email, String password);
    void enableUser(String email);
    void lockUser(String email);
    void unlockUser(String email);
    List<User> getAllUsers();
    List<User> getLockedUsers();
    List<User> getUnlockedUsers();
    void deleteUser(String email);
}
