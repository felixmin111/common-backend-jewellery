package com.autowise.demo.controller;

import com.autowise.demo.dto.CraftDto;
import com.autowise.demo.service.CraftService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crafts")
@RequiredArgsConstructor
public class CraftController {

    private final CraftService craftService;

    @GetMapping
    public List<CraftDto> getAll() {
        return craftService.getAll();
    }

    @GetMapping("/{id}")
    public CraftDto getById(@PathVariable Long id) {
        return craftService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CraftDto create(@Valid @RequestBody CraftDto request) {

        System.out.println("shopName = " + request.getShopName());
        System.out.println("nrc      = " + request.getNrc());

        return craftService.create(request);
    }



    @PutMapping("/{id}")
    public CraftDto update(@PathVariable Long id, @Valid @RequestBody CraftDto request) {
        return craftService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        craftService.delete(id);
    }
}
