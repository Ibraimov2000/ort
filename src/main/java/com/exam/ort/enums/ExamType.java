package com.exam.ort.enums;

import lombok.Getter;

@Getter
public enum ExamType {
    MAIN("Основной тест"), ADDITIONAL("Дополнительный");

    private final String value;

    ExamType(String value) {
        this.value = value;
    }
}
