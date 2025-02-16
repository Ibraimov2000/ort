package com.exam.ort.service.impl;

import com.exam.ort.entity.Progress;
import com.exam.ort.entity.User;
import com.exam.ort.model.UserRecord;
import com.exam.ort.repository.ProgressRepository;
import com.exam.ort.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final ProgressRepository progressRepository;


    public Progress getUserProgress(Long userId, Long examId) {
        return progressRepository.findByUserIdAndExamId(userId, examId);
    }

    public double getUserScorePercentage(Long userId) {
        Progress progress = progressRepository.findByUserId(userId);
        return progress.getScorePercentage();
    }

    @Override
    public List<UserRecord> getTopUsers() {
        return null; //TODO
    }
}
