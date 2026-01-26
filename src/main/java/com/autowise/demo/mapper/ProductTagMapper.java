package com.autowise.demo.mapper;

import com.autowise.demo.dto.ProductTagDto;
import com.autowise.demo.model.ProductTag;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductTagMapper {

    ProductTagDto toDto(ProductTag entity);

    ProductTag toEntity(ProductTagDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ProductTagDto dto, @MappingTarget ProductTag entity);
}
