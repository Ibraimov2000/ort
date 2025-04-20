package com.exam.ort.model;

import java.time.LocalDateTime;

public record AchievementRecord(
        Long id,
        UserRecord userRecord,
        String description,
        LocalDateTime awardedAt) {
}
