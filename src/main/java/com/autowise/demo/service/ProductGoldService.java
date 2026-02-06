package com.autowise.demo.service;

import com.autowise.demo.dto.ProductGoldDto;
import com.autowise.demo.mapper.ProductGoldMapper;
import com.autowise.demo.model.*;
import com.autowise.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductGoldService {

    private final ProductGoldRepository productGoldRepository;
    private final ProductGoldMapper productGoldMapper;

    private final ProductRepository productRepository;
    private final GoldSourceRepository goldSourceRepository;
    private final CraftRepository craftRepository;

    public List<ProductGoldDto> getAll() {
        return productGoldRepository.findAll()
                .stream()
                .map(productGoldMapper::toDto)
                .toList();
    }

    public ProductGoldDto getById(Long id) {
        ProductGold pg = productGoldRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductGold not found: " + id));
        return productGoldMapper.toDto(pg);
    }

    public ProductGoldDto create(ProductGoldDto request) {
        ProductGold entity = productGoldMapper.toEntity(request);

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found: " + request.getProductId()));

        GoldSource goldSource = goldSourceRepository.findById(request.getGoldSourceId())
                .orElseThrow(() -> new RuntimeException("Gold source not found: " + request.getGoldSourceId()));

        Craft craft = craftRepository.findById(request.getCraftId())
                .orElseThrow(() -> new RuntimeException("Craft not found: " + request.getCraftId()));

        entity.setProduct(product);
        entity.setGoldSource(goldSource);
        entity.setCraft(craft);

        ProductGold saved = productGoldRepository.save(entity);
        return productGoldMapper.toDto(saved);
    }

    public ProductGoldDto update(Long id, ProductGoldDto request) {
        ProductGold entity = productGoldRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductGold not found: " + id));

        productGoldMapper.updateEntityFromDto(request, entity);

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found: " + request.getProductId()));

        GoldSource goldSource = goldSourceRepository.findById(request.getGoldSourceId())
                .orElseThrow(() -> new RuntimeException("Gold source not found: " + request.getGoldSourceId()));

        Craft craft = craftRepository.findById(request.getCraftId())
                .orElseThrow(() -> new RuntimeException("Craft not found: " + request.getCraftId()));

        entity.setProduct(product);
        entity.setGoldSource(goldSource);
        entity.setCraft(craft);

        ProductGold saved = productGoldRepository.save(entity);
        return productGoldMapper.toDto(saved);
    }

    public void delete(Long id) {
        if (!productGoldRepository.existsById(id)) {
            throw new RuntimeException("ProductGold not found: " + id);
        }
        productGoldRepository.deleteById(id);
    }
}
