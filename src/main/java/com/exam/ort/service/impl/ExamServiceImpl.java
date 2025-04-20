package com.exam.ort.service.impl;

import com.exam.ort.entity.Exam;
import com.exam.ort.entity.Section;
import com.exam.ort.enums.ExamType;
import com.exam.ort.mapper.ExamMapper;
import com.exam.ort.mapper.SectionMapper;
import com.exam.ort.model.ExamRecord;
import com.exam.ort.repository.ExamRepository;
import com.exam.ort.repository.specification.ExamSpecification;
import com.exam.ort.service.ExamService;
import com.exam.ort.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
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
    final SectionMapper sectionMapper;

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

    @Override
    @Transactional
    public ExamRecord update(ExamRecord examRequest) {

        Exam exam = examRepository.findById(examRequest.id())
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found"));

        exam.setName(examRequest.name());
        exam.setDescription(examRequest.description());
        exam.setExamType(examRequest.examType());
        exam.setDuration(examRequest.duration());
        exam.setLanguage(examRequest.language());

        List<Section> sections = examRequest.sections().stream()
                .map(sectionMapper::toEntity)
                .peek(section -> {
                    section.setExam(exam);

                    if (section.getId() == null) {
                        section.setCreated(LocalDateTime.now());
                    } else {
                        section.setUpdated(LocalDateTime.now());
                    }

                    if (section.getQuestions() != null) {
                        section.getQuestions().forEach(question -> {
                            question.setSection(section);

                            if (question.getId() == null) {
                                question.setCreatedAt(LocalDateTime.now());
                            } else {
                                question.setUpdatedAt(LocalDateTime.now());
                            }

                            if (question.getAnswers() != null) {
                                question.getAnswers().forEach(answer -> {
                                    answer.setQuestion(question);

                                    if (answer.getId() == null) {
                                        answer.setCreatedAt(LocalDateTime.now());
                                    } else {
                                        answer.setUpdatedAt(LocalDateTime.now());
                                    }
                                });
                            }
                        });
                    }
                })
                .toList();

        exam.getSections().clear();
        exam.getSections().addAll(sections);

        Exam saved = examRepository.save(exam);
        return examMapper.toRecord(saved);
    }

}
