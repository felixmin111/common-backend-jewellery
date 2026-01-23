package com.autowise.demo.mapper;

import com.autowise.demo.dto.UserDto;
import com.autowise.demo.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    // Entity -> DTO
    @Mapping(target = "password", ignore = true) // don't expose
    @Mapping(target = "token", ignore = true)    // don't expose
    UserDto toDto(User user);

    // DTO -> Entity
    @Mapping(target = "id", ignore = true)           // usually DB-generated
    @Mapping(target = "hashPassword", ignore = true) // set in service (encode)
    @Mapping(target = "role", ignore = true)         // set in service or admin flow
    User toEntity(UserDto dto);

    // DTO -> Entity (update)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hashPassword", ignore = true)
    @Mapping(target = "role", ignore = true)
    void updateEntityFromDto(UserDto dto, @MappingTarget User user);
}
