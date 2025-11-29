package com.autowise.demo.mapper;

import com.autowise.demo.dto.CategoryDto;
import com.autowise.demo.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDto toDto(Category category) {
        if (category == null) return null;

        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .code(category.getCode())
                .build();
    }

    public Category toEntity(CategoryDto dto) {
        if (dto == null) return null;

        return Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .code(dto.getCode())
                .build();
    }

    public void updateEntityFromDto(CategoryDto dto, Category category) {
        if (dto == null || category == null) return;

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setCode(dto.getCode());
    }
}