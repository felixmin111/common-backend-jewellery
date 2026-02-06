package com.autowise.demo.controller;

import com.autowise.demo.dto.PromotionDto;
import com.autowise.demo.service.PromotionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService service;

    @GetMapping
    public List<PromotionDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public PromotionDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PromotionDto create(@Valid @RequestBody PromotionDto request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public PromotionDto update(@PathVariable Long id, @Valid @RequestBody PromotionDto request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}