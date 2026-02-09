package com.autowise.demo.mapper;

import com.autowise.demo.dto.GoldPriceHistoryDto;
import com.autowise.demo.model.GoldPriceHistory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface GoldPriceHistoryMapper {

    GoldPriceHistoryDto toDto(GoldPriceHistory entity);

    GoldPriceHistory toEntity(GoldPriceHistoryDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(
            GoldPriceHistoryDto dto,
            @MappingTarget GoldPriceHistory entity
    );
}