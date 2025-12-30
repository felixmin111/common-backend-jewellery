package com.autowise.demo.mapper;

import com.autowise.demo.dto.GemsPackageDto;
import com.autowise.demo.model.GemsPackage;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface GemsPackageMapper {

    @Mapping(source = "gemType.id", target = "gemTypeId")
    @Mapping(source = "gemType.name", target = "gemTypeName")
    GemsPackageDto toDto(GemsPackage entity);

    @Mapping(target = "gemType", ignore = true)
    GemsPackage toEntity(GemsPackageDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "gemType", ignore = true)
    void updateEntityFromDto(GemsPackageDto dto, @MappingTarget GemsPackage entity);
}
