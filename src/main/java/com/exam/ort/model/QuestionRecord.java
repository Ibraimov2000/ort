package com.exam.ort.model;

import java.util.List;

public record QuestionRecord(Long id, String text, List<AnswerRecord> answers, AnswerRecord correctAnswer, int score, SectionRecord section) {
}
