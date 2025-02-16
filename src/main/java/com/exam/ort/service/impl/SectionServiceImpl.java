package com.exam.ort.service.impl;

import com.exam.ort.mapper.SectionMapper;
import com.exam.ort.model.SectionRecord;
import com.exam.ort.repository.SectionRepository;
import com.exam.ort.service.SectionService;
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
public class SectionServiceImpl implements SectionService {

    final SectionRepository sectionRepository;
    final SectionMapper sectionMapper;

    @Override
    public SectionRecord save(SectionRecord sectionRecord) {
        log.info("Saving section: {}", sectionRecord.name());

        if (sectionRecord.questionsCount() <= 0) {
            throw new IllegalArgumentException("Section must have at least one question.");
        }

        return sectionMapper.toRecord(sectionRepository.save(sectionMapper.toEntity(sectionRecord)));
    }

    @Override
    public SectionRecord findById(long id) {
        log.info("Finding section by ID: {}", id);
        return sectionMapper.toRecord(
                sectionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Section not found"))
        );
    }

    @Override
    public List<SectionRecord> findAllByExamId(long examId) {
        log.info("Fetching all sections by examId: {}", examId);
        return sectionRepository.findAllByExamId(examId).stream().map(sectionMapper::toRecord).collect(Collectors.toList());
    }

    @Override
    public List<SectionRecord> findAllByExamId() {
        log.info("Fetching all sections.");
        return sectionRepository.findAll().stream().map(sectionMapper::toRecord).collect(Collectors.toList());
    }

    @Override
    public void deleteById(long id) {
        log.info("Deleting section by ID: {}", id);
        if (!sectionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Section not found");
        }
        sectionRepository.deleteById(id);
    }
}
