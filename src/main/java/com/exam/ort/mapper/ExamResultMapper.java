package com.exam.ort.mapper;

import com.exam.ort.entity.ExamResult;
import com.exam.ort.model.ExamResultRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ExamMapper.class, UserMapper.class})
public interface ExamResultMapper {

    ExamResultRecord toRecord(ExamResult examResult);
    ExamResult toEntity(ExamResultRecord examResultRecord);
}
