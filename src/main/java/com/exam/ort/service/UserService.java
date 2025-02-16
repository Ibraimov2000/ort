package com.exam.ort.service;

import com.exam.ort.model.UserRecord;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {

    void deleteById(long id);
    List<UserRecord> findAll();
    UserRecord findById(long id);
    UserDetails loadUserByUsername(String username);

    UserRecord save(UserRecord userRecord);

    UserRecord getCurrentUser();
}
