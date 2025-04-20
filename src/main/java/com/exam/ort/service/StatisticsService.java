package com.exam.ort.service;

import com.exam.ort.entity.Progress;
import com.exam.ort.model.UserRecord;

import java.util.List;

public interface StatisticsService {
    Progress getUserProgress(Long userId, Long examId);
    double getUserScorePercentage(Long userId);

    List<UserRecord> getTopUsers();
}
