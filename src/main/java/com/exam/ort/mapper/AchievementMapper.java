package com.exam.ort.mapper;

import com.exam.ort.entity.Achievement;
import com.exam.ort.model.AchievementRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AchievementMapper {

    AchievementRecord toRecord(Achievement achievement);
    Achievement toEntity(AchievementRecord achievementRecord);
}
