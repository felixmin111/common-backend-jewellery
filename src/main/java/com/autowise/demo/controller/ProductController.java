package com.autowise.demo.controller;

import com.autowise.demo.dto.ProductDto;
import com.autowise.demo.dto.ProductImageDto;
import com.autowise.demo.mapper.ProductMapper;
import com.autowise.demo.repository.ProductRepository;
import com.autowise.demo.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @GetMapping
    public List<ProductDto> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto create(@Valid @RequestBody ProductDto request) {
        return productService.create(request);
    }

    @PutMapping("/{id}")
    public ProductDto update(@PathVariable Long id, @Valid @RequestBody ProductDto request) {
        return productService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping("/type/{typeId}")
    public List<ProductDto> getByType(@PathVariable Long typeId) {
        return productService.getProductsByTypeId(typeId);
    }
    @PostMapping("/{id}/images")
    public ProductDto addProductImage(@PathVariable Long id,
                                      @RequestBody ProductImageDto req) {
        return productService.addProductImage(id, req);
    }

    @DeleteMapping("/images/{imageId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductImage(@PathVariable Long imageId) {
        productService.deleteProductImage(imageId);
    }

}
