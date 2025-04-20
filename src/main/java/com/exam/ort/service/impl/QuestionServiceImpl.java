package com.exam.ort.service.impl;

import com.exam.ort.mapper.QuestionMapper;
import com.exam.ort.model.QuestionRecord;
import com.exam.ort.repository.QuestionRepository;
import com.exam.ort.service.QuestionService;
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
public class QuestionServiceImpl implements QuestionService {

    final QuestionRepository questionRepository;
    final QuestionMapper questionMapper;

    @Override
    public QuestionRecord save(QuestionRecord questionRecord) {
        log.info("Saving question: {}", questionRecord.text());

        if (questionRecord.text() == null || questionRecord.text().isEmpty()) {
            throw new IllegalArgumentException("Question text cannot be empty.");
        }
        if (questionRecord.score() <= 0) {
            throw new IllegalArgumentException("Question score must be greater than zero.");
        }

        return questionMapper.toRecord(questionRepository.save(questionMapper.toEntity(questionRecord)));
    }

    @Override
    public List<QuestionRecord> findAll() {
        log.info("Fetching all questions.");
        return questionRepository.findAll().stream().map(questionMapper::toRecord).collect(Collectors.toList());
    }

    @Override
    public QuestionRecord findById(long id) {
        log.info("Finding question by ID: {}", id);
        return questionMapper.toRecord(
                questionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Question not found"))
        );
    }

    @Override
    public void deleteById(long id) {
        log.info("Deleting question by ID: {}", id);
        if (!questionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Question not found");
        }
        questionRepository.deleteById(id);
    }

    @Override
    public List<QuestionRecord> findAllBySectionId(Long sectionId) {
        return questionRepository.findAllBySectionId(sectionId).stream().map(questionMapper::toRecord).collect(Collectors.toList());
    }
}
