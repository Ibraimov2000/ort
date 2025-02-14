package com.exam.ort.service;

import com.exam.ort.model.ExamRecord;

import java.util.List;

public interface ExamService {
    ExamRecord save(ExamRecord examRecord);
    ExamRecord findById(long id);
    List<ExamRecord> findAll();
    void deleteById(long id);

}
