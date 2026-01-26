package com.autowise.demo.controller;

import com.autowise.demo.dto.ProductTagDto;
import com.autowise.demo.service.ProductTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product-tags")
public class ProductTagController {

    private final ProductTagService service;

    @GetMapping
    public List<ProductTagDto> getAll() {
        return service.findAll();
    }

    @PostMapping
    public ProductTagDto create(@RequestBody ProductTagDto req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public ProductTagDto update(@PathVariable Long id, @RequestBody ProductTagDto req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
