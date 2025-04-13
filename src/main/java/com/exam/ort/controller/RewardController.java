package com.exam.ort.controller;

import com.exam.ort.model.RewardRecord;
import com.exam.ort.model.UserRecord;
import com.exam.ort.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rewards")
@RequiredArgsConstructor
public class RewardController {

    final RewardService rewardService;

    @GetMapping
    public List<RewardRecord> getRewards(@AuthenticationPrincipal UserRecord userRecord) {
        return rewardService.getRewardsForUser(userRecord);
    }

    @PostMapping("/assign")
    public ResponseEntity<?> assignReward(@RequestBody RewardRecord rewardRecord, @AuthenticationPrincipal UserRecord userRecord) {
        rewardService.assignRewardToUser(userRecord, rewardRecord);
        return ResponseEntity.ok("Reward assigned successfully");
    }
}
