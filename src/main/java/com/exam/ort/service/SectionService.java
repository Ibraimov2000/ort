package com.exam.ort.service;

import com.exam.ort.model.SectionRecord;
import com.exam.ort.model.request.SectionRequest;

import java.util.List;

public interface SectionService {

    SectionRecord save(SectionRecord sectionRecord);
    SectionRecord findById(long id);
    List<SectionRecord> findAllByExamId(long examId);
    List<SectionRecord> findAll();
    void deleteById(long id);

    SectionRecord update(Long id, SectionRequest section);
}
