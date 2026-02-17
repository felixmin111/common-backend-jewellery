package com.autowise.demo.mapper;

import com.autowise.demo.dto.SellerDto;
import com.autowise.demo.model.Seller;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SellerMapper {

    SellerDto toDto(Seller entity);

    Seller toEntity(SellerDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "goldSources", ignore = true) // ✅ important
    void updateEntityFromDto(SellerDto dto, @MappingTarget Seller entity);
}
