package com.exam.ort.model;

import java.time.LocalDateTime;

public record ExamResultRecord(long id, UserRecord userRecord, ExamRecord examRecord, int score, LocalDateTime passedDateTime) {
}
