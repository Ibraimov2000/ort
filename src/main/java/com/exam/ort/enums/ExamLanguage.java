package com.exam.ort.enums;


import lombok.Getter;

@Getter
public enum ExamLanguage {
    RU("русский"), KG("кыргызча");

    private final String value;

    ExamLanguage(String value) {
        this.value = value;
    }
}
