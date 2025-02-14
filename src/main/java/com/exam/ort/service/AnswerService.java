package com.exam.ort.service;

import com.exam.ort.model.AnswerRecord;

import java.util.List;

public interface AnswerService {

    AnswerRecord save(AnswerRecord record);
    List<AnswerRecord> findAll();
    AnswerRecord findById(long id);
    void deleteById(long id);

    List<AnswerRecord> findAnswersByQuestionId(long questionId);
}
