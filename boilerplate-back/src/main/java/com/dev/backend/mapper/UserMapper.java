package com.dev.backend.mapper;

import com.dev.backend.dto.UserDto;
import com.dev.backend.model.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User entity);

    User toEntity(UserDto dto);


}