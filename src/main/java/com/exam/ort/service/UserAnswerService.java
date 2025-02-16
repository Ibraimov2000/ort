package com.exam.ort.service;


import com.exam.ort.model.UserAnswerRecord;

import java.util.List;

public interface UserAnswerService {
    UserAnswerRecord save(UserAnswerRecord userAnswerRecord);
    UserAnswerRecord findById(long id);
    List<UserAnswerRecord> findAll();
    void deleteById(long id);

    void
    submitAnswers(List<UserAnswerRecord> answers);
}
