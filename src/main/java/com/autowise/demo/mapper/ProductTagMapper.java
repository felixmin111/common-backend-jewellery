// src/main/java/com/autowise/demo/mapper/ProductTagMapper.java
package com.autowise.demo.mapper;

import com.autowise.demo.dto.ProductTagDto;
import com.autowise.demo.model.ProductTag;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductTagMapper {

    /* ---------- Entity → DTO ---------- */
    ProductTagDto toDto(ProductTag entity);

    List<ProductTagDto> toDtoList(List<ProductTag> entities);

    /* ---------- DTO → Entity ---------- */
    ProductTag toEntity(ProductTagDto dto);

    /* ---------- Update existing entity ---------- */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ProductTagDto dto, @MappingTarget ProductTag entity);
}
