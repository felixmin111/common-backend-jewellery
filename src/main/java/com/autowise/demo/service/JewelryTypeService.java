package com.autowise.demo.service;

import com.autowise.demo.dto.JewelryTypeDto;
import com.autowise.demo.mapper.JewelryTypeMapper;
import com.autowise.demo.model.Category;
import com.autowise.demo.model.JewelryType;
import com.autowise.demo.repository.CategoryRepository;
import com.autowise.demo.repository.JewelryTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class JewelryTypeService {

    private final JewelryTypeRepository typeRepo;
    private final CategoryRepository categoryRepo;
    private final JewelryTypeMapper mapper; // ✅ inject mapper

    public List<JewelryTypeDto> getAll() {
        return typeRepo.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public JewelryTypeDto getById(Long id) {
        JewelryType t = typeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JewelryType not found: " + id));
        return mapper.toDto(t);
    }

    public List<JewelryTypeDto> getByCategory(Long categoryId) {
        return typeRepo.findAllByCategoryIdOrderByNameAsc(categoryId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public JewelryTypeDto create(JewelryTypeDto dto) {
        Category category = categoryRepo.findById(dto.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + dto.categoryId()));

        if (typeRepo.existsByCategoryIdAndNameIgnoreCase(dto.categoryId(), dto.name())) {
            throw new IllegalArgumentException("Type already exists under this category.");
        }

        JewelryType t = mapper.toEntity(dto);
        t.setCategory(category); // ✅ set category
        // ✅ imageUrl already inside dto -> entity mapping

        t = typeRepo.save(t);
        return mapper.toDto(t);
    }

    public JewelryTypeDto update(Long id, JewelryTypeDto dto) {
        JewelryType existing = typeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JewelryType not found: " + id));

        Category category = categoryRepo.findById(dto.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + dto.categoryId()));

        boolean changedCategory = !existing.getCategory().getId().equals(dto.categoryId());
        boolean changedName = !existing.getName().equalsIgnoreCase(dto.name());

        if ((changedCategory || changedName) &&
                typeRepo.existsByCategoryIdAndNameIgnoreCase(dto.categoryId(), dto.name())) {
            throw new IllegalArgumentException("Type already exists under this category.");
        }

        mapper.updateEntityFromDto(dto, existing); // ✅ update fields (name, imageUrl)
        existing.setCategory(category);

        existing = typeRepo.save(existing);
        return mapper.toDto(existing);
    }

    public void delete(Long id) {
        if (!typeRepo.existsById(id)) {
            throw new EntityNotFoundException("JewelryType not found: " + id);
        }
        typeRepo.deleteById(id);
    }
}