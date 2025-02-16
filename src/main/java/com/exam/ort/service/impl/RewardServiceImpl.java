package com.exam.ort.service.impl;

import com.exam.ort.entity.Reward;
import com.exam.ort.entity.User;
import com.exam.ort.mapper.RewardMapper;
import com.exam.ort.model.RewardRecord;
import com.exam.ort.model.UserRecord;
import com.exam.ort.repository.RewardRepository;
import com.exam.ort.repository.UserRepository;
import com.exam.ort.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {

    private final RewardRepository rewardRepository;
    private final UserRepository userRepository;
    private final RewardMapper rewardMapper;

    @Override
    public void assignRewardToUser(UserRecord userRecord, RewardRecord rewardRecord) {

        User user = userRepository.findById(userRecord.id()).orElseThrow();

        Reward reward = new Reward();
        reward.setName(rewardRecord.name());
        reward.setDescription(rewardRecord.description());
        reward.setPoints(rewardRecord.points());
        reward.setUser(user);
        rewardRepository.save(reward);
    }

    @Override
    public List<RewardRecord> getRewardsForUser(UserRecord userRecord) {
        return rewardRepository.findAllByUserId(userRecord.id()).stream().map(rewardMapper::toRecord).collect(Collectors.toList());
    }
}
