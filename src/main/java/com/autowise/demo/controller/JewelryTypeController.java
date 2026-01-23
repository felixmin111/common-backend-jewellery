package com.autowise.demo.controller;

import com.autowise.demo.dto.JewelryTypeDto;
import com.autowise.demo.service.JewelryTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jewelry-types")
public class JewelryTypeController {

    private final JewelryTypeService jewelryTypeService;


    @GetMapping
    public List<JewelryTypeDto> getAll(
            @RequestParam(required = false) Long categoryId
    ) {
        if (categoryId != null) {
            return jewelryTypeService.getByCategory(categoryId);
        }
        return jewelryTypeService.getAll();
    }


    @GetMapping("/{id}")
    public JewelryTypeDto getById(@PathVariable Long id) {
        return jewelryTypeService.getById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JewelryTypeDto create(@Valid @RequestBody JewelryTypeDto dto) {
        return jewelryTypeService.create(dto);
    }


    @PutMapping("/{id}")
    public JewelryTypeDto update(
            @PathVariable Long id,
            @Valid @RequestBody JewelryTypeDto dto
    ) {
        return jewelryTypeService.update(id, dto);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        jewelryTypeService.delete(id);
    }
}