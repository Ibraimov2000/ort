package com.exam.ort.service.impl;

import com.exam.ort.mapper.UserAnswerMapper;
import com.exam.ort.model.UserAnswerRecord;
import com.exam.ort.repository.UserAnswerRepository;
import com.exam.ort.service.UserAnswerService;
import com.exam.ort.exception.ResourceNotFoundException;
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
public class UserAnswerServiceImpl implements UserAnswerService {

    final UserAnswerRepository userAnswerRepository;
    final UserAnswerMapper userAnswerMapper;

    @Override
    public UserAnswerRecord save(UserAnswerRecord userAnswerRecord) {

        log.info("Saving user answer for question: {}", userAnswerRecord.questionRecord().text());

        if (userAnswerRecord.answerRecord() == null) {
            throw new IllegalArgumentException("Answer cannot be null.");
        }
        if (userAnswerRecord.userRecord() == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        return userAnswerMapper.toRecord(userAnswerRepository.save(userAnswerMapper.toEntity(userAnswerRecord)));
    }

    @Override
    public UserAnswerRecord findById(long id) {
        log.info("Finding user answer by ID: {}", id);
        return userAnswerMapper.toRecord(
                userAnswerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User answer not found"))
        );
    }

    @Override
    public List<UserAnswerRecord> findAll() {
        log.info("Fetching all user answers.");
        return userAnswerRepository.findAll().stream().map(userAnswerMapper::toRecord).collect(Collectors.toList());
    }

    @Override
    public void deleteById(long id) {
        log.info("Deleting user answer by ID: {}", id);
        if (!userAnswerRepository.existsById(id)) {
            throw new ResourceNotFoundException("User answer not found");
        }
        userAnswerRepository.deleteById(id);
    }

    @Override
    public void submitAnswers(List<UserAnswerRecord> answers) {

    }
}
