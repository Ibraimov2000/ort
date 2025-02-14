package com.exam.ort.mapper;

import com.exam.ort.entity.Exam;
import com.exam.ort.model.ExamRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {SectionMapper.class})
public interface ExamMapper {

    Exam toEntity(ExamRecord examRecord);
    ExamRecord toRecord(Exam exam);
}
