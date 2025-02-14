package com.exam.ort.service.impl;

import com.exam.ort.mapper.SectionMapper;
import com.exam.ort.model.SectionRecord;
import com.exam.ort.repository.SectionRepository;
import com.exam.ort.service.SectionService;
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
        return sectionMapper.toRecord(sectionRepository.save(sectionMapper.toEntity(sectionRecord)));
    }

    @Override
    public SectionRecord findById(long id) {
        return sectionMapper.toRecord(sectionRepository.findById(id).orElseThrow());
    }

    @Override
    public List<SectionRecord> findAll() {
        return sectionRepository.findAll().stream().map(sectionMapper::toRecord).collect(Collectors.toList());
    }

    @Override
    public void deleteById(long id) {
        sectionRepository.deleteById(id);
    }
}
