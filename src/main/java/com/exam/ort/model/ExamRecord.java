package com.exam.ort.model;

import com.exam.ort.enums.ExamLanguage;
import com.exam.ort.enums.ExamType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Duration;
import java.util.List;

public record ExamRecord(
        Long id,
        @NotNull @Size(min = 1, max = 255) String name,
        @NotNull ExamType examType,
        String description,
        ExamLanguage language,
        @NotNull Duration duration,
        List<SectionRecord> sections
) {
}
