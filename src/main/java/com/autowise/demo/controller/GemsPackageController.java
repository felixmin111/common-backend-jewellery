package com.autowise.demo.controller;

import com.autowise.demo.dto.GemsPackageDto;
import com.autowise.demo.service.GemsPackageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gems-packages")
@RequiredArgsConstructor
public class GemsPackageController {

    private final GemsPackageService service;

    @GetMapping
    public List<GemsPackageDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GemsPackageDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GemsPackageDto create(@Valid @RequestBody GemsPackageDto req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public GemsPackageDto update(@PathVariable Long id, @Valid @RequestBody GemsPackageDto req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
