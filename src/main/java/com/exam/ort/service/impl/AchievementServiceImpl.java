package com.exam.ort.service.impl;

import com.exam.ort.entity.Achievement;
import com.exam.ort.entity.User;
import com.exam.ort.mapper.AchievementMapper;
import com.exam.ort.model.AchievementRecord;
import com.exam.ort.repository.AchievementRepository;
import com.exam.ort.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository achievementRepository;
    private final AchievementMapper achievementMapper;

    @Override
    public List<AchievementRecord> getAchievementsForUser(User user) {
        // Логика для получения достижений пользователя
        return achievementRepository.findAllByUser(user).stream().map(achievementMapper::toRecord).collect(Collectors.toList());
    }
}

