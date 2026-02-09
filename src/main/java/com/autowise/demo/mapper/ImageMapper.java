package com.autowise.demo.mapper;

import com.autowise.demo.dto.ImageDto;
import com.autowise.demo.model.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {

    public ImageDto toDto(Image entity) {
        if (entity == null) return null;

        return ImageDto.builder()
                .id(entity.getId())
                .url(entity.getUrl())
                .build();
    }

    public Image toEntity(String url) {
        return Image.builder()
                .url(url)
                .build();
    }
}