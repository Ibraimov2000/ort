package com.exam.ort.mapper;

import com.exam.ort.entity.Answer;
import com.exam.ort.model.AnswerRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    Answer toEntity(AnswerRecord answerRecord);

    @Mapping(target = "question", ignore = true)
    AnswerRecord toRecord(Answer answer);
}
