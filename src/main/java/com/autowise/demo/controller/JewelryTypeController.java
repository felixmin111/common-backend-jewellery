package com.autowise.demo.controller;

import com.autowise.demo.dto.JewelryTypeDto;
import com.autowise.demo.service.JewelryTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jewelry-types")
@RequiredArgsConstructor
public class JewelryTypeController {

    private final JewelryTypeService service;

    @GetMapping
    public List<JewelryTypeDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public JewelryTypeDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/by-category/{categoryId}")
    public List<JewelryTypeDto> getByCategory(@PathVariable Long categoryId) {
        return service.getByCategory(categoryId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JewelryTypeDto create(@RequestBody @Valid JewelryTypeDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public JewelryTypeDto update(@PathVariable Long id, @RequestBody @Valid JewelryTypeDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}