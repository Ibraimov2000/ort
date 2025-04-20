package com.exam.ort.model;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UserAnswerRecord(
        Long id,
        @NotNull UserRecord userRecord,
        @NotNull QuestionRecord questionRecord,
        @NotNull AnswerRecord answerRecord,
        boolean isCorrect,
        @NotNull LocalDateTime createdAt
) {
}
