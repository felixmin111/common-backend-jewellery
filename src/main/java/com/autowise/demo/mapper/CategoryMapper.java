package com.autowise.demo.mapper;

import com.autowise.demo.dto.CategoryDto;
import com.autowise.demo.model.Category;
import org.springframework.stereotype.Component;

import com.autowise.demo.dto.CategoryDto;
import com.autowise.demo.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);
    Category toEntity(CategoryDto dto);
    void updateEntityFromDto(CategoryDto dto, @MappingTarget Category category);
}
