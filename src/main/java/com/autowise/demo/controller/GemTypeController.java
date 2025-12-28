package com.autowise.demo.controller;

import com.autowise.demo.dto.GemTypeDto;
import com.autowise.demo.service.GemTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gem-types")
@RequiredArgsConstructor
public class GemTypeController {

    private final GemTypeService service;

    @GetMapping
    public List<GemTypeDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GemTypeDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GemTypeDto create(@Valid @RequestBody GemTypeDto req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public GemTypeDto update(@PathVariable Long id, @Valid @RequestBody GemTypeDto req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
