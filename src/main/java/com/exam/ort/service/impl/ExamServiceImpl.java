package com.exam.ort.service.impl;

import com.exam.ort.entity.Exam;
import com.exam.ort.enums.ExamType;
import com.exam.ort.mapper.ExamMapper;
import com.exam.ort.model.ExamRecord;
import com.exam.ort.repository.ExamRepository;
import com.exam.ort.repository.specification.ExamSpecification;
import com.exam.ort.service.ExamService;
import com.exam.ort.exception.ResourceNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        log.info("Saving exam: {}", examRecord.name());
        return examMapper.toRecord(examRepository.save(examMapper.toEntity(examRecord)));
    }

    @Override
    public ExamRecord findById(long id) {
        log.info("Finding exam by ID: {}", id);
        return examMapper.toRecord(
                examRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Exam not found"))
        );
    }

    @Override
    public List<ExamRecord> findAll(String type, String language) {
        Specification<Exam> spec = Specification.where(ExamSpecification.hasType(type))
                .and(ExamSpecification.hasLanguage(language));
        return examRepository.findAll(spec).stream().map(examMapper::toRecord).collect(Collectors.toList());
    }

    @Override
    public void deleteById(long id) {
        log.info("Deleting exam by ID: {}", id);
        if (!examRepository.existsById(id)) {
            throw new ResourceNotFoundException("Exam not found");
        }
        examRepository.deleteById(id);
    }

    @Override
    public void checkIfTestExpired(Long examId) {
        Exam exam = examRepository.findById(examId).orElseThrow(() -> new RuntimeException("Test not found"));
        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(exam.getEndTime())) {
            finishTest(exam.getId());
        }
    }

    @Override
    public void finishTest(Long examId) {
        Exam exam = examRepository.findById(examId).orElseThrow(() -> new RuntimeException("Test not found"));
        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(exam.getEndTime())) {
            // Завершаем тест, сохраняем результаты
            // Например, подсчитываем баллы и отправляем уведомления
        }
    }


    @Override
    public LocalDateTime getEndTime(Long examId) {
        Exam exam = examRepository.findById(examId).orElseThrow(() -> new RuntimeException("Test not found"));
        return exam.getEndTime();
    }

}
