package com.exam.ort.mapper;

import com.exam.ort.entity.UserAnswer;
import com.exam.ort.model.UserAnswerRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAnswerMapper {

    UserAnswer toEntity(UserAnswerRecord userAnswerRecord);
    UserAnswerRecord toRecord(UserAnswer userAnswer);
}
