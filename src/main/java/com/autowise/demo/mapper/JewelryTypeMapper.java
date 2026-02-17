package com.autowise.demo.mapper;


import com.autowise.demo.dto.JewelryTypeDto;
import com.autowise.demo.model.JewelryType;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JewelryTypeMapper {

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    JewelryTypeDto toDto(JewelryType entity);

    // We don’t map category here (we will set category in service)
    @Mapping(target = "category", ignore = true)
    JewelryType toEntity(JewelryTypeDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    void updateEntityFromDto(JewelryTypeDto dto, @MappingTarget JewelryType entity);
}