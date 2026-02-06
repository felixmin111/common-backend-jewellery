package com.autowise.demo.controller;

import com.autowise.demo.dto.GoldSourceDto;
import com.autowise.demo.service.GoldSourceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gold-source")
@RequiredArgsConstructor
public class GoldSourceController {

    private final GoldSourceService service;

    @GetMapping
    public List<GoldSourceDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GoldSourceDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GoldSourceDto create(@Valid @RequestBody GoldSourceDto request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public GoldSourceDto update(@PathVariable Long id, @Valid @RequestBody GoldSourceDto request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
