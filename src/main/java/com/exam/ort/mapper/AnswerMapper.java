package com.exam.ort.mapper;

import com.exam.ort.entity.Answer;
import com.exam.ort.model.AnswerRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {QuestionMapper.class})
public interface AnswerMapper {

    Answer toEntity(AnswerRecord answerRecord);
    AnswerRecord toRecord(Answer answer);
}
