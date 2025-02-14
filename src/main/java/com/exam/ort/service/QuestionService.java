package com.exam.ort.service;

import com.exam.ort.model.QuestionRecord;

import java.util.List;

public interface QuestionService {

    QuestionRecord save(QuestionRecord record);
    List<QuestionRecord> findAll();
    QuestionRecord findById(long id);
    void deleteById(long id);
}
