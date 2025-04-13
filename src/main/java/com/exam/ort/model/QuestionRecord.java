package com.exam.ort.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record QuestionRecord(
        Long id,
        @NotNull @Size(min = 1) String text,
        @NotNull List<AnswerRecord> answers,
        @NotNull AnswerRecord correctAnswer,
        int score,
        @NotNull SectionRecord section
) {
}
