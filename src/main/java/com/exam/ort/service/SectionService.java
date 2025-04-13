package com.exam.ort.service;

import com.exam.ort.model.SectionRecord;

import java.util.List;

public interface SectionService {

    SectionRecord save(SectionRecord sectionRecord);
    SectionRecord findById(long id);
    List<SectionRecord> findAllByExamId(long examId);
    List<SectionRecord> findAllByExamId();
    void deleteById(long id);
}
