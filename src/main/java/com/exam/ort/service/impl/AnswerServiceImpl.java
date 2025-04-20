package com.exam.ort.service.impl;

import com.exam.ort.mapper.AnswerMapper;
import com.exam.ort.model.AnswerRecord;
import com.exam.ort.repository.AnswerRepository;
import com.exam.ort.service.AnswerService;
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
public class AnswerServiceImpl implements AnswerService {

    final AnswerRepository answerRepository;
    final AnswerMapper answerMapper;

    @Override
    public AnswerRecord save(AnswerRecord answerRecord) {
        log.info("Saving answer: {}", answerRecord.text());

        if (answerRecord.text() == null || answerRecord.text().isEmpty()) {
            throw new IllegalArgumentException("Answer text cannot be empty.");
        }

        return answerMapper.toRecord(answerRepository.save(answerMapper.toEntity(answerRecord)));
    }

    @Override
    public List<AnswerRecord> findAll() {
        log.info("Fetching all answers.");
        return answerRepository.findAll().stream().map(answerMapper::toRecord).collect(Collectors.toList());
    }

    @Override
    public AnswerRecord findById(long id) {
        log.info("Finding answer by ID: {}", id);
        return answerMapper.toRecord(
                answerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Answer not found"))
        );
    }

    @Override
    public void deleteById(long id) {
        log.info("Deleting answer by ID: {}", id);
        if (!answerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Answer not found");
        }
        answerRepository.deleteById(id);
    }

    @Override
    public List<AnswerRecord> findAnswersByQuestionId(long questionId) {
        log.info("Finding answers for question ID: {}", questionId);
        return answerRepository.findAnswersByQuestionId(questionId).stream()
                .map(answerMapper::toRecord)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnswerRecord> findAllByQuestionId(Long questionId) {
        return answerRepository.findAnswersByQuestionId(questionId).stream().map(answerMapper::toRecord).collect(Collectors.toList());
    }
}
