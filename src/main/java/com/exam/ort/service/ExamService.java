package com.exam.ort.service;

import com.exam.ort.entity.Exam;
import com.exam.ort.model.ExamRecord;

import java.time.LocalDateTime;
import java.util.List;

public interface ExamService {
    ExamRecord save(ExamRecord examRecord);
    ExamRecord findById(long id);
    List<ExamRecord> findAll(String type, String language);
    void deleteById(long id);
    void checkIfTestExpired(Long examId);
    void finishTest(Long examId);

    LocalDateTime getEndTime(Long examId);
}
