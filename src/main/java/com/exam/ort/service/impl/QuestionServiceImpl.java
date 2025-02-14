package com.exam.ort.service.impl;

import com.exam.ort.mapper.QuestionMapper;
import com.exam.ort.model.QuestionRecord;
import com.exam.ort.repository.QuestionRepository;
import com.exam.ort.service.QuestionService;
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
        return questionMapper.toRecord(questionRepository.save(questionMapper.toEntity(questionRecord)));
    }

    @Override
    public List<QuestionRecord> findAll() {
        return questionRepository.findAll().stream().map(questionMapper::toRecord).collect(Collectors.toList());
    }

    @Override
    public QuestionRecord findById(long id) {
        return questionMapper.toRecord(questionRepository.findById(id).orElseThrow());
    }

    @Override
    public void deleteById(long id) {
        questionRepository.deleteById(id);
    }
}
