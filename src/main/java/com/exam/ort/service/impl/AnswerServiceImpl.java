package com.exam.ort.service.impl;

import com.exam.ort.entity.Answer;
import com.exam.ort.mapper.AnswerMapper;
import com.exam.ort.mapper.QuestionMapper;
import com.exam.ort.model.AnswerRecord;
import com.exam.ort.repository.AnswerRepository;
import com.exam.ort.repository.QuestionRepository;
import com.exam.ort.service.AnswerService;
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
        return answerMapper.toRecord(answerRepository.save(answerMapper.toEntity(answerRecord)));
    }

    @Override
    public List<AnswerRecord> findAll() {
        return answerRepository.findAll().stream().map(answerMapper::toRecord).collect(Collectors.toList());
    }

    @Override
    public AnswerRecord findById(long id) {
        return answerMapper.toRecord(answerRepository.findById(id).orElseThrow());
    }

    @Override
    public void deleteById(long id) {
        answerRepository.deleteById(id);
    }

    @Override
    public List<AnswerRecord> findAnswersByQuestionId(long questionId) {
        return answerRepository.findAnswersByQuestionId(questionId).stream().map(answerMapper::toRecord).collect(Collectors.toList());
    }
}
