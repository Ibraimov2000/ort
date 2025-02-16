package com.exam.ort.service;


import com.exam.ort.model.RewardRecord;
import com.exam.ort.model.UserRecord;

import java.util.List;

public interface RewardService {
    void assignRewardToUser(UserRecord userRecord, RewardRecord rewardRecord);

    List<RewardRecord> getRewardsForUser(UserRecord userRecord);
}
