package com.exam.ort.model;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ExamResultRecord(
        long id,
        @NotNull UserRecord userRecord,
        @NotNull ExamRecord examRecord,
        int score,
        @NotNull LocalDateTime passedDateTime
) {
}
