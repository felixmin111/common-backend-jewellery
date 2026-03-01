package com.autowise.demo.mapper;

import com.autowise.demo.dto.ProductTagDto;
import com.autowise.demo.model.ProductTag;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.web.bind.annotation.Mapping;

@Mapper(componentModel = "spring")
public interface ProductTagMapper {

    ProductTagDto toDto(ProductTag entity);

    ProductTag toEntity(ProductTagDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ProductTagDto dto, @MappingTarget ProductTag entity);
}
