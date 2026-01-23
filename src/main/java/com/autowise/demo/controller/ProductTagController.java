// src/main/java/com/autowise/demo/controller/ProductTagController.java
package com.autowise.demo.controller;

import com.autowise.demo.dto.ProductTagDto;
import com.autowise.demo.service.ProductTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-tags")
@RequiredArgsConstructor
@CrossOrigin
public class ProductTagController {

    private final ProductTagService service;

    @GetMapping
    public List<ProductTagDto> getAll() {
        return service.findAll();
    }

    @PostMapping
    public ProductTagDto create(@RequestBody ProductTagDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public ProductTagDto update(
            @PathVariable Long id,
            @RequestBody ProductTagDto dto
    ) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
