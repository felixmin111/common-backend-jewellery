package com.autowise.demo.service;

import com.autowise.demo.dto.*;
import com.autowise.demo.mapper.ProductMapper;
import com.autowise.demo.model.*;
import com.autowise.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    private final GoldSourceRepository goldSourceRepository;
    private final CraftRepository craftRepository;
    private final GemsPackageRepository gemsPackageRepository;

    public List<ProductDto> getAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toDto)
                .toList();
    }

    public ProductDto getById(Long id) {
        Product p = productRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));
        return productMapper.toDto(p);
    }

    public ProductDto create(ProductDto request) {
        Product entity = productMapper.toEntity(request);

        applyGoldRows(entity, request.getProductGolds());
        applyJewelleryRows(entity, request.getProductJewellerys());

        Product saved = productRepository.save(entity);
        return productMapper.toDto(saved);
    }

    public ProductDto update(Long id, ProductDto request) {
        Product existing = productRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));

        productMapper.updateEntityFromDto(request, existing);

        // replace rows
        existing.getProductGolds().clear();
        existing.getProductJewellerys().clear();

        applyGoldRows(existing, request.getProductGolds());
        applyJewelleryRows(existing, request.getProductJewellerys());

        Product saved = productRepository.save(existing);
        return productMapper.toDto(saved);
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found: " + id);
        }
        productRepository.deleteById(id);
    }

    private void applyGoldRows(Product product, java.util.Set<ProductGoldItemDto> goldItems) {
        if (goldItems == null) return;

        if (product.getProductGolds() == null) {
            product.setProductGolds(new LinkedHashSet<>());
        }

        for (ProductGoldItemDto item : goldItems) {
            GoldSource goldSource = goldSourceRepository.findById(item.getGoldSourceId())
                    .orElseThrow(() -> new RuntimeException("GoldSource not found: " + item.getGoldSourceId()));

            Craft craft = craftRepository.findById(item.getCraftId())
                    .orElseThrow(() -> new RuntimeException("Craft not found: " + item.getCraftId()));

            ProductGold pg = ProductGold.builder()
                    .product(product)
                    .goldSource(goldSource)
                    .craft(craft)
                    .weight(item.getWeight())
                    .goldPurity(item.getGoldPurity())
                    .build();

            product.getProductGolds().add(pg);
        }
    }

    private void applyJewelleryRows(Product product, java.util.Set<ProductJewelleryItemDto> items) {
        if (items == null) return;

        if (product.getProductJewellerys() == null) {
            product.setProductJewellerys(new LinkedHashSet<>());
        }

        for (ProductJewelleryItemDto item : items) {
            GemsPackage gp = gemsPackageRepository.findById(item.getGemsPackageId())
                    .orElseThrow(() -> new RuntimeException("GemsPackage not found: " + item.getGemsPackageId()));

            ProductJewellery pj = ProductJewellery.builder()
                    .product(product)
                    .gemsPackage(gp)
                    .qty(item.getQty())
                    .sellingPrice(item.getSellingPrice())
                    .build();

            product.getProductJewellerys().add(pj);
        }
    }
}
