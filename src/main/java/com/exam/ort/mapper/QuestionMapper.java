package com.exam.ort.mapper;

import com.exam.ort.entity.Question;
import com.exam.ort.model.QuestionRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AnswerMapper.class, SectionMapper.class})
public interface QuestionMapper {

    Question toEntity(QuestionRecord questionRecord);
    QuestionRecord toRecord(Question question);
}
