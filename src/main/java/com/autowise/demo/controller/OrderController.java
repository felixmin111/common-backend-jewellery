package com.autowise.demo.controller;

import com.autowise.demo.dto.OrderDto;
import com.autowise.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto create(@RequestBody OrderDto req,
                           @RequestParam(defaultValue = "true") boolean allowBackorder) {
        return orderService.create(req, allowBackorder);
    }

    @GetMapping("/{id}")
    public OrderDto getById(@PathVariable Long id) {
        return orderService.getById(id);
    }
    @GetMapping
    public List<OrderDto> getAll() {
        return orderService.getAll();
    }
}