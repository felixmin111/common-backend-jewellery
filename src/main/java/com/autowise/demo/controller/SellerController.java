package com.autowise.demo.controller;

import com.autowise.demo.dto.SellerDto;
import com.autowise.demo.service.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sellers")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService service;

    @GetMapping
    public List<SellerDto> getAll() {
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SellerDto create(@Valid @RequestBody SellerDto request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public SellerDto update(@PathVariable Long id, @Valid @RequestBody SellerDto request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
