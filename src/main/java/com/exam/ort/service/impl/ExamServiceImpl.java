package com.exam.ort.service.impl;

import com.exam.ort.mapper.ExamMapper;
import com.exam.ort.model.ExamRecord;
import com.exam.ort.repository.ExamRepository;
import com.exam.ort.service.ExamService;
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
public class ExamServiceImpl implements ExamService {

    final ExamRepository examRepository;
    final ExamMapper examMapper;

    @Override
    public ExamRecord save(ExamRecord examRecord) {
        return examMapper.toRecord(examRepository.save(examMapper.toEntity(examRecord)));
    }

    @Override
    public ExamRecord findById(long id) {
        return examMapper.toRecord(examRepository.findById(id).orElseThrow());
    }

    @Override
    public List<ExamRecord> findAll() {
        return examRepository.findAll().stream().map(examMapper::toRecord).collect(Collectors.toList());
    }

    @Override
    public void deleteById(long id) {
        examRepository.deleteById(id);
    }
}
