package com.autowise.demo.controller;

import com.autowise.demo.dto.InvoiceResponseDto;
import com.autowise.demo.dto.PurchaseSaveRequestDto;
import com.autowise.demo.service.InvoiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public InvoiceResponseDto create(@RequestBody PurchaseSaveRequestDto req) {
        return invoiceService.create(req);
    }

    @GetMapping
    public List<InvoiceResponseDto> list() {
        return invoiceService.list();
    }

    @GetMapping("/{id}")
    public InvoiceResponseDto get(@PathVariable Long id) {
        return invoiceService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        invoiceService.delete(id);
    }
}