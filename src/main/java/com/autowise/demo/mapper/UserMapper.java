package com.autowise.demo.mapper;

import com.autowise.demo.dto.CategoryDto;
import com.autowise.demo.dto.UserDto;
import com.autowise.demo.model.Category;
import com.autowise.demo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto dto);
    void updateEntityFromDto(UserDto dto, @MappingTarget User user);
}
