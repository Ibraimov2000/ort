package com.exam.ort.service;

import com.exam.ort.model.UserRecord;

import java.util.List;

public interface UserService {

    UserRecord save(UserRecord user);
    void deleteById(long id);
    List<UserRecord> findAll();
    UserRecord findById(long id);
}
