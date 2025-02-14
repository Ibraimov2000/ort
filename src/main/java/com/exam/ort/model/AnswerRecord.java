package com.exam.ort.model;

public record AnswerRecord (Long id, String text, boolean isCorrect, QuestionRecord question) {
}
