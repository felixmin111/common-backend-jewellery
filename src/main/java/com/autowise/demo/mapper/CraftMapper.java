package com.autowise.demo.mapper;

import com.autowise.demo.dto.CraftDto;
import com.autowise.demo.model.Craft;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CraftMapper {

    CraftDto toDto(Craft entity);

    Craft toEntity(CraftDto dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(CraftDto dto, @MappingTarget Craft entity);
}
