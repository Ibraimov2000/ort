package com.exam.ort.mapper;

import com.exam.ort.entity.Answer;
import com.exam.ort.model.AnswerRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    @Mapping(source = "isCorrect", target = "correct")
    @Mapping(source = "question", target = "question")
    Answer toEntity(AnswerRecord answerRecord);

    @Mapping(target = "question", ignore = true)
    @Mapping(target = "isCorrect", source = "correct")
    AnswerRecord toRecord(Answer answer);
}
