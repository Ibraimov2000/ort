package com.exam.ort.model;

import com.exam.ort.enums.ExamLanguage;
import com.exam.ort.enums.ExamType;

import java.time.Duration;
import java.util.List;

public record ExamRecord(Long id, String name, ExamType examType, String description, ExamLanguage examLanguage, Duration duration, List<SectionRecord> sections) {
}
