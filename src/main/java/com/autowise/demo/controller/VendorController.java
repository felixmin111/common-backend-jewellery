package com.autowise.demo.controller;

import com.autowise.demo.dto.VendorDto;
import com.autowise.demo.dto.VendorInvoiceLookupDto;
import com.autowise.demo.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;

    @GetMapping
    public List<VendorDto> getAll() {
        return vendorService.getAll();
    }

    @GetMapping("/{id}")
    public VendorDto getById(@PathVariable Long id) {
        return vendorService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDto create(@Validated @RequestBody VendorDto request) {
        return vendorService.create(request);
    }

    @PutMapping("/{id}")
    public VendorDto update(
            @PathVariable Long id,
            @Validated @RequestBody VendorDto request
    ) {
        return vendorService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        vendorService.delete(id);
    }
    @GetMapping("/invoice/{invoiceNo}")
    public VendorInvoiceLookupDto getInvoiceForBuyback(@PathVariable String invoiceNo) {
        return vendorService.findInvoiceForBuyback(invoiceNo);
    }
}