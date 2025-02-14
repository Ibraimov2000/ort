package com.exam.ort.mapper;

import com.exam.ort.entity.User;
import com.exam.ort.model.UserRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRecord userRecord);
    UserRecord toRecord(User user);
}
