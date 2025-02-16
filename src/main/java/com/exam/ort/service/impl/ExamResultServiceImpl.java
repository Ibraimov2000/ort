package com.exam.ort.service.impl;

import com.exam.ort.mapper.ExamResultMapper;
import com.exam.ort.model.ExamResultRecord;
import com.exam.ort.repository.ExamResultRepository;
import com.exam.ort.service.ExamResultService;
import com.exam.ort.exception.ResourceNotFoundException;
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

        log.info("Saving exam result for user: {}", examResultRecord.userRecord().email());

        if (examResultRecord.score() < 0) {
            throw new IllegalArgumentException("Score cannot be negative.");
        }

        return examResultMapper.toRecord(examResultRepository.save(examResultMapper.toEntity(examResultRecord)));
    }

    @Override
    public ExamResultRecord findById(Long id) {
        log.info("Finding exam result by ID: {}", id);
        return examResultMapper.toRecord(
                examResultRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Exam result not found"))
        );
    }

    @Override
    public List<ExamResultRecord> findAll() {
        log.info("Fetching all exam results.");
        return examResultRepository.findAll().stream().map(examResultMapper::toRecord).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting exam result by ID: {}", id);
        if (!examResultRepository.existsById(id)) {
            throw new ResourceNotFoundException("Exam result not found");
        }
        examResultRepository.deleteById(id);
    }

    @Override
    public List<ExamResultRecord> findAllByExamIdAndUserId(Long examId, Long userId) {
        return null; //examResultRepository.findAllByExamIdAndUserId(examId, userId);
    }
}
