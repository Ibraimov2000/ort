package com.exam.ort.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AnswerRecord (
        Long id,
        @NotNull @NotBlank String text,
        boolean isCorrect,
        @NotNull QuestionRecord question
) {
}
