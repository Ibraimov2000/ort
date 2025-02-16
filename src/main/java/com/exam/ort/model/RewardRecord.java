package com.exam.ort.model;

public record RewardRecord(
        Long id,
        String name,
        String description,
        int points,
        UserRecord user
) {
}
