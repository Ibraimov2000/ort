package com.exam.ort.controller;

import com.exam.ort.entity.Achievement;
import com.exam.ort.entity.User;
import com.exam.ort.model.AchievementRecord;
import com.exam.ort.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/achievements")
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;

    @GetMapping
    public List<AchievementRecord> getAchievements(@AuthenticationPrincipal User user) {
        return achievementService.getAchievementsForUser(user);
    }
}

