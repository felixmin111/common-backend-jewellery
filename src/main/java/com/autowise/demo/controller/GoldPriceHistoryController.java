package com.autowise.demo.controller;

import com.autowise.demo.dto.GoldPriceHistoryDto;
import com.autowise.demo.service.GoldPriceHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gold-price-history")
@RequiredArgsConstructor
public class GoldPriceHistoryController {

    private final GoldPriceHistoryService service;

    @GetMapping
    public List<GoldPriceHistoryDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/active")
    public GoldPriceHistoryDto getActive() {
        return service.getActive();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GoldPriceHistoryDto create(
            @Valid @RequestBody GoldPriceHistoryDto dto
    ) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public GoldPriceHistoryDto update(
            @PathVariable Long id,
            @Valid @RequestBody GoldPriceHistoryDto dto
    ) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}