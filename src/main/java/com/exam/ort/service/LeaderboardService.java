package com.exam.ort.service;


import com.exam.ort.entity.Leaderboard;

import java.util.List;

public interface LeaderboardService {
    List<Leaderboard> getTopUsers();
    void updateLeaderboard(Long user, int score);
}
