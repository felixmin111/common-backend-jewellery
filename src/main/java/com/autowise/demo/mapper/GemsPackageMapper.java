package com.autowise.demo.mapper;

import com.autowise.demo.dto.GemsPackageDto;
import com.autowise.demo.model.GemsPackage;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface GemsPackageMapper {

    @Mapping(source = "gemType.id", target = "gemTypeId")
    @Mapping(source = "gemType.name", target = "gemTypeName")
    @Mapping(source = "certificateImages", target = "certificateImages")
    GemsPackageDto toDto(GemsPackage entity);

    @Mapping(target = "gemType", ignore = true)
    @Mapping(target = "certificateImages", ignore = true) // ✅ important
    GemsPackage toEntity(GemsPackageDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "gemType", ignore = true)
    @Mapping(target = "certificateImages", ignore = true) // ✅ important
    void updateEntityFromDto(GemsPackageDto dto, @MappingTarget GemsPackage entity);



}