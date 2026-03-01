package com.autowise.demo.service;

import com.autowise.demo.dto.VendorDto;
import com.autowise.demo.mapper.VendorMapper;
import com.autowise.demo.model.Vendor;
import com.autowise.demo.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VendorService {

    private final VendorMapper vendorMapper;
    private final VendorRepository vendorRepository;

    public List<VendorDto> getAll() {
        return vendorRepository.findAll()
                .stream()
                .map(vendorMapper::toDto)
                .toList();
    }

    public VendorDto getById(Long id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found: " + id));
        return vendorMapper.toDto(vendor);
    }

    public VendorDto create(VendorDto request) {
        Vendor vendor = vendorMapper.toEntity(request);
        Vendor saved = vendorRepository.save(vendor);
        return vendorMapper.toDto(saved);
    }

    public VendorDto update(Long id, VendorDto request) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found: " + id));

        vendorMapper.updateEntityFromDto(request, vendor);
        return vendorMapper.toDto(vendor);
    }

    public void delete(Long id) {
        if (!vendorRepository.existsById(id)) {
            throw new RuntimeException("Vendor not found: " + id);
        }
        vendorRepository.deleteById(id);
    }
}