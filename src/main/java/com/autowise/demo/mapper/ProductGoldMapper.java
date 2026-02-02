package com.autowise.demo.mapper;

import com.autowise.demo.dto.ProductGoldDto;
import com.autowise.demo.model.ProductGold;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductGoldMapper {

    // ✅ IDs + display names
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "goldSource.id", target = "goldSourceId")
    @Mapping(source = "craft.id", target = "craftId")

    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "goldSource.name", target = "goldSourceName")
    @Mapping(source = "craft.shopName", target = "craftShopName")
    ProductGoldDto toDto(ProductGold entity);

    // ✅ for create/update: we set relations in service (by ids)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "goldSource", ignore = true)
    @Mapping(target = "craft", ignore = true)
    ProductGold toEntity(ProductGoldDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "goldSource", ignore = true)
    @Mapping(target = "craft", ignore = true)
    void updateEntityFromDto(ProductGoldDto dto, @MappingTarget ProductGold entity);
}
