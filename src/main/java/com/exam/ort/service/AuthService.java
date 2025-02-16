package com.exam.ort.service;

import com.exam.ort.model.UserRecord;

public interface AuthService {
    String register(UserRecord user);
    String login(String email, String password);
}
