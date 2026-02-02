package com.autowise.demo.mapper;

import com.autowise.demo.dto.GoldSourceDto;
import com.autowise.demo.model.GoldSource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GoldSourceMapper {

    GoldSourceDto toDto(GoldSource entity);

    GoldSource toEntity(GoldSourceDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(GoldSourceDto dto, @MappingTarget GoldSource entity);
}
