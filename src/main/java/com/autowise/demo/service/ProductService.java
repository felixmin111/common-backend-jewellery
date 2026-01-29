package com.autowise.demo.service;

import com.autowise.demo.dto.ProductDto;
import com.autowise.demo.mapper.ProductMapper;
import com.autowise.demo.model.Product;
import com.autowise.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public List<ProductDto> getAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    public ProductDto getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));
        return productMapper.toDto(product);
    }

    public ProductDto create(ProductDto request) {
        request.setName(trimOrNull(request.getName()));
        request.setCode(trimOrNull(request.getCode()));
        request.setStockStatus(trimOrNull(request.getStockStatus()));
        request.setDesc(trimOrNull(request.getDesc()));
        request.setCollection(trimOrNull(request.getCollection()));
        request.setShortDesc(trimOrNull(request.getShortDesc()));
        request.setColor(trimOrNull(request.getColor()));

        if (productRepository.existsByName(request.getName())) {
            throw new RuntimeException("Product with this name already exists: " + request.getName());
        }

        Product entity = productMapper.toEntity(request);
        Product saved = productRepository.save(entity);
        return productMapper.toDto(saved);
    }

    public ProductDto update(Long id, ProductDto request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));

        request.setName(trimOrNull(request.getName()));
        request.setCode(trimOrNull(request.getCode()));
        request.setStockStatus(trimOrNull(request.getStockStatus()));
        request.setDesc(trimOrNull(request.getDesc()));
        request.setCollection(trimOrNull(request.getCollection()));
        request.setShortDesc(trimOrNull(request.getShortDesc()));
        request.setColor(trimOrNull(request.getColor()));

        productMapper.updateEntityFromDto(request, product);

        Product saved = productRepository.save(product);
        return productMapper.toDto(saved);
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found: " + id);
        }
        productRepository.deleteById(id);
    }

    private String trimOrNull(String s) {
        if (s == null) return null;
        s = s.trim();
        return s.isEmpty() ? null : s;
    }
}
