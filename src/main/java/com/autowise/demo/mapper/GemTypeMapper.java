package com.autowise.demo.mapper;

import com.autowise.demo.dto.GemTypeDto;
import com.autowise.demo.model.GemType;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GemTypeMapper {
    GemTypeDto toDto(GemType entity);
    GemType toEntity(GemTypeDto dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(GemTypeDto dto, @MappingTarget GemType entity);
}
