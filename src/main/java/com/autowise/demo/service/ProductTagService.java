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

    private final ProductTagRepository repo;
    private final ProductTagMapper mapper;

    public List<ProductTagDto> findAll() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    public ProductTagDto create(ProductTagDto req) {
        String name = req.getName() == null ? "" : req.getName().trim();
        String desc = req.getDescription() == null ? null : req.getDescription().trim();

        if (name.isBlank()) throw new RuntimeException("Tag name is required");
        if (repo.existsByNameIgnoreCase(name)) throw new RuntimeException("Tag name already exists");

        ProductTag entity = ProductTag.builder()
                .name(name)
                .description(desc != null && desc.isBlank() ? null : desc)
                .build();

        ProductTag saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    public ProductTagDto update(Long id, ProductTagDto req) {
        String name = req.getName() == null ? "" : req.getName().trim();
        String desc = req.getDescription() == null ? null : req.getDescription().trim();

        if (name.isBlank()) throw new RuntimeException("Tag name is required");

        ProductTag tag = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));

        // unique check (ignore itself)
        repo.findByNameIgnoreCase(name).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new RuntimeException("Tag name already exists");
            }
        });

        // use mapper update
        ProductTagDto cleanDto = ProductTagDto.builder()
                .name(name)
                .description(desc != null && desc.isBlank() ? null : desc)
                .build();

        mapper.updateEntityFromDto(cleanDto, tag);

        ProductTag saved = repo.save(tag);
        return mapper.toDto(saved);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new RuntimeException("Tag not found");
        repo.deleteById(id);
    }
}
