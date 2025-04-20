package com.exam.ort.model.request;

import com.exam.ort.enums.ExamLanguage;
import com.exam.ort.enums.ExamType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ExamCreateRequest(
        @NotNull @Size(min = 1, max = 255) String name,
        @NotNull ExamType examType,
        String description,
        @NotNull ExamLanguage language,
        @NotNull Integer durationInMinutes
) {}
