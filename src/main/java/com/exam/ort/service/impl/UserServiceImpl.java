package com.exam.ort.service.impl;

import com.exam.ort.mapper.UserMapper;
import com.exam.ort.model.UserRecord;
import com.exam.ort.repository.UserRepository;
import com.exam.ort.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;
    final UserMapper userMapper;

    @Override
    public UserRecord save(UserRecord userRecord) {
        return userMapper.toRecord(userRepository.save(userMapper.toEntity(userRecord)));
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserRecord> findAll() {
        return userRepository.findAll().stream().map(userMapper::toRecord).collect(Collectors.toList());
    }

    @Override
    public UserRecord findById(long id) {
        return userMapper.toRecord(userRepository.findById(id).orElseThrow());
    }
}
