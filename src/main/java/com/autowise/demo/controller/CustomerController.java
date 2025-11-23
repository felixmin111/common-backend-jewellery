package com.autowise.demo.controller;

import com.autowise.demo.dto.CustomerDto;
import com.autowise.demo.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @GetMapping
    public List<CustomerDto> getAll() {
        return customerService.getAll();
    }

    @GetMapping("/{id}")
    public CustomerDto getById(@PathVariable Long id) {
        return customerService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto create( @RequestBody CustomerDto request) {
        return customerService.create(request);
    }

    @PutMapping("/{id}")
    public CustomerDto update(@PathVariable Long id,
                                   @Validated @RequestBody CustomerDto request) {
        return customerService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        customerService.delete(id);
    }
}

