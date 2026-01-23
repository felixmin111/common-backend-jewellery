// src/main/java/com/autowise/demo/service/ProductTagService.java
package com.autowise.demo.service;

import com.autowise.demo.dto.ProductTagDto;
import com.autowise.demo.mapper.ProductTagMapper;
import com.autowise.demo.model.ProductTag;
import com.autowise.demo.repository.ProductTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductTagService {

    private final ProductTagRepository repository;
    private final ProductTagMapper mapper;

    public List<ProductTagDto> findAll() {
        return mapper.toDtoList(repository.findAll());
    }

    public ProductTagDto create(ProductTagDto dto) {
        if (repository.existsByName(dto.getName())) {
            throw new RuntimeException("Product tag already exists");
        }

        ProductTag entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    public ProductTagDto update(Long id, ProductTagDto dto) {
        ProductTag entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product tag not found"));

        mapper.updateEntityFromDto(dto, entity);
        return mapper.toDto(repository.save(entity));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
