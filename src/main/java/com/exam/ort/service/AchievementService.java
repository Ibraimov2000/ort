package com.exam.ort.service;

import com.exam.ort.entity.User;
import com.exam.ort.model.AchievementRecord;

import java.util.List;

public interface AchievementService {
    List<AchievementRecord> getAchievementsForUser(User user);
}
