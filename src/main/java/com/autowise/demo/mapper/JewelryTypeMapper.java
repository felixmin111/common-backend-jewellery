package com.autowise.demo.mapper;


import com.autowise.demo.dto.JewelryTypeDto;
import com.autowise.demo.model.Category;
import com.autowise.demo.model.JewelryType;

public class JewelryTypeMapper {

    private JewelryTypeMapper() {}

    public static JewelryTypeDto toDto(JewelryType e) {
        return new JewelryTypeDto(
                e.getId(),
                e.getName(),
                e.getCategory().getId(),
                e.getCategory().getName()
        );
    }

    public static void apply(JewelryType e, JewelryTypeDto dto, Category category) {
        e.setName(dto.name().trim());
        e.setCategory(category);
    }
}