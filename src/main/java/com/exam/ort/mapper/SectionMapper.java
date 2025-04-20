package com.exam.ort.mapper;

import com.exam.ort.entity.Section;
import com.exam.ort.model.SectionRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {QuestionMapper.class})
public interface SectionMapper {

    Section toEntity(SectionRecord sectionRecord);
    @Mapping(target = "exam", ignore = true)
    SectionRecord toRecord(Section section);
}
