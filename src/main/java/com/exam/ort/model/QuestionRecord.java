package com.exam.ort.model;

import com.exam.ort.enums.QuestionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record QuestionRecord(
        Long id,

        int number,
        @NotNull @Size(min = 1) String text,
        @NotNull List<AnswerRecord> answers,
        @NotNull QuestionType questionType,
        int score,
        @NotNull SectionRecord section,
        byte[] imageData
) {
}
