package com.autowise.demo.controller;

import com.autowise.demo.dto.ProductGoldDto;
import com.autowise.demo.service.ProductGoldService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-gold")
@RequiredArgsConstructor
public class ProductGoldController {

    private final ProductGoldService productGoldService;

    @GetMapping
    public List<ProductGoldDto> getAll() {
        return productGoldService.getAll();
    }

    @GetMapping("/{id}")
    public ProductGoldDto getById(@PathVariable Long id) {
        return productGoldService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductGoldDto create(@Valid @RequestBody ProductGoldDto request) {
        return productGoldService.create(request);
    }

    @PutMapping("/{id}")
    public ProductGoldDto update(@PathVariable Long id, @Valid @RequestBody ProductGoldDto request) {
        return productGoldService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productGoldService.delete(id);
    }
}
