package com.exam.ort.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record SectionRecord(
        Long id,
        @NotNull @Size(min = 1) String name,
        @Min(0) int questionsCount,
        @NotNull ExamRecord exam,
        @NotNull List<QuestionRecord> questions
) {
}
