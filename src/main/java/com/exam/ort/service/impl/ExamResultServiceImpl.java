package com.exam.ort.service.impl;

import com.exam.ort.mapper.ExamResultMapper;
import com.exam.ort.model.ExamResultRecord;
import com.exam.ort.repository.ExamResultRepository;
import com.exam.ort.service.ExamResultService;
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
public class ExamResultServiceImpl implements ExamResultService {

    final ExamResultRepository examResultRepository;
    final ExamResultMapper examResultMapper;

    @Override
    public ExamResultRecord save(ExamResultRecord examResultRecord) {
        return examResultMapper.toRecord(examResultRepository.save(examResultMapper.toEntity(examResultRecord)));
    }

    @Override
    public ExamResultRecord findById(Long id) {
        return examResultMapper.toRecord(examResultRepository.findById(id).orElseThrow());
    }

    @Override
    public List<ExamResultRecord> findAll() {
        return examResultRepository.findAll().stream().map(examResultMapper::toRecord).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        examResultRepository.deleteById(id);
    }
}
