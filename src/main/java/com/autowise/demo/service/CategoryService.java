package com.autowise.demo.service;

import com.autowise.demo.dto.CategoryDto;
import com.autowise.demo.exceptions.NotFoundException;
import com.autowise.demo.mapper.CategoryMapper;
import com.autowise.demo.model.Category;
import com.autowise.demo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found: " + id));
        return categoryMapper.toDto(category);
    }

    public CategoryDto create(CategoryDto request) {
        Category category = categoryMapper.toEntity(request);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toDto(saved);
    }

    public CategoryDto update(Long id, CategoryDto request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found: " + id));
        categoryMapper.updateEntityFromDto(request, category);
        return categoryMapper.toDto(category);
    }

    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found: " + id);
        }
        categoryRepository.deleteById(id);
    }
}