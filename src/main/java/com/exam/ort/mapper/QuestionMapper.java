package com.exam.ort.mapper;

import com.exam.ort.entity.Question;
import com.exam.ort.model.QuestionRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AnswerMapper.class})
public interface QuestionMapper {

    Question toEntity(QuestionRecord questionRecord);

    @Mapping(target = "section", ignore = true)
    QuestionRecord toRecord(Question question);
}
