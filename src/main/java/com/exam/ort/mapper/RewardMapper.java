package com.exam.ort.mapper;

import com.exam.ort.entity.Reward;
import com.exam.ort.model.RewardRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RewardMapper {

    RewardRecord toRecord(Reward reward);
    Reward toEntity(RewardRecord rewardRecord);
}
