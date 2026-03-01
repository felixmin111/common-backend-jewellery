package com.autowise.demo.mapper;

import com.autowise.demo.dto.*;
import com.autowise.demo.model.*;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    // ✅ IMPORTANT: ignore relations + audit fields when mapping DTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)

    // ✅ ignore collections because we create ProductGold/ProductJewellery in service
    @Mapping(target = "productGolds", ignore = true)
    @Mapping(target = "productJewellerys", ignore = true)
    @Mapping(target = "productImages", ignore = true)
    Product toEntity(ProductDto dto);

    // ✅ Entity -> DTO: map collections manually
    @Mapping(target = "productGolds", expression = "java(mapGoldItems(entity.getProductGolds()))")
    @Mapping(target = "productJewellerys", expression = "java(mapJewelleryItems(entity.getProductJewellerys()))")
    ProductDto toDto(Product entity);

    // ✅ update basic fields only
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "productGolds", ignore = true)
    @Mapping(target = "productJewellerys", ignore = true)
    @Mapping(target = "productImages", ignore = true)
    @Mapping(target="finalPrice", source="finalPrice")
    void updateEntityFromDto(ProductDto dto, @MappingTarget Product entity);

    // ---------------- helpers ----------------

    default Set<ProductGoldItemDto> mapGoldItems(Set<ProductGold> rows) {
        if (rows == null) return null;
        return rows.stream().map(r -> ProductGoldItemDto.builder()
                .id(r.getId())
                .goldSourceId(r.getGoldSource().getId())
                .craftId(r.getCraft().getId())
                .weight(r.getWeight())
                .goldPurity(r.getGoldPurity())
                .goldSourceName(r.getGoldSource().getName())
                .craftShopName(r.getCraft().getShopName())
                .build()
        ).collect(Collectors.toSet());
    }

    default Set<ProductJewelleryItemDto> mapJewelleryItems(Set<ProductJewellery> rows) {
        if (rows == null) return null;
        return rows.stream().map(r -> ProductJewelleryItemDto.builder()
                .id(r.getId())
                .gemsPackageId(r.getGemsPackage().getId())
                .qty(r.getQty())
                .sellingPrice(r.getSellingPrice())
                .gemsPackageName(r.getGemsPackage().getName())
                .originalPrice(r.getGemsPackage().getOriginalPrice())
                .unitWeight(r.getGemsPackage().getGemsWeight())
                .build()
        ).collect(Collectors.toSet());
    }
    default Set<ProductImageDto> mapImages(Set<ProductImage> rows) {
        if (rows == null) return null;

        return rows.stream()
                .map(r -> ProductImageDto.builder()
                        .id(r.getId())
                        .imageUrl(r.getImageUrl())
                        .build())
                .collect(Collectors.toSet());
    }
}
