package com.autowise.demo.mapper;

import com.autowise.demo.dto.SellerDto;
import com.autowise.demo.model.Seller;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SellerMapper {

    SellerDto toDto(Seller entity);

    Seller toEntity(SellerDto dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(SellerDto dto, @MappingTarget Seller entity);
}
