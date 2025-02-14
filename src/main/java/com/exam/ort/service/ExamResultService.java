package com.exam.ort.service;

import com.exam.ort.model.ExamResultRecord;

import java.util.List;

public interface ExamResultService {

    ExamResultRecord save(ExamResultRecord record);
    ExamResultRecord findById(Long id);
    List<ExamResultRecord> findAll();
    void deleteById(Long id);
}
