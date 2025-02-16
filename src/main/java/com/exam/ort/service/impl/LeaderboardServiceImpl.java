package com.exam.ort.service.impl;

import com.exam.ort.entity.Leaderboard;
import com.exam.ort.entity.User;
import com.exam.ort.repository.LeaderboardRepository;
import com.exam.ort.repository.UserRepository;
import com.exam.ort.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaderboardServiceImpl implements LeaderboardService {

    private final LeaderboardRepository leaderboardRepository;
    private final UserRepository userRepository;

    @Override
    public List<Leaderboard> getTopUsers() {
        return leaderboardRepository.findTop10ByOrderByScoreDesc();
    }
    @Override
    public void updateLeaderboard(Long userId, int score) {
        List<Leaderboard> leaderboard = leaderboardRepository.findAll();
        User user = userRepository.findById(userId).orElseThrow();

        // Пример, как можно пересчитать рейтинг (в зависимости от условий)
        int rank = calculateRank(leaderboard, score);

        Leaderboard entry = new Leaderboard();
        entry.setUser(user);
        entry.setScore(score);
        entry.setRank(rank);
        leaderboardRepository.save(entry);
    }

    private int calculateRank(List<Leaderboard> leaderboard, int score) {
        // Логика для вычисления ранга на основе результатов
        return leaderboard.size() + 1;  // Пример, ранжируем по количеству пользователей
    }
}
