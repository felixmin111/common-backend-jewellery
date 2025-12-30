package com.autowise.demo.service;

import com.autowise.demo.dto.SellerDto;
import com.autowise.demo.mapper.SellerMapper;
import com.autowise.demo.model.Seller;
import com.autowise.demo.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerService {

    private final SellerRepository repo;
    private final SellerMapper mapper;

    public List<SellerDto> getAll() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    public SellerDto getById(Long id) {
        return mapper.toDto(requireEntity(id));
    }

    public SellerDto create(SellerDto request) {
        Seller saved = repo.save(mapper.toEntity(request));
        return mapper.toDto(saved);
    }

    public SellerDto update(Long id, SellerDto request) {
        Seller entity = requireEntity(id);
        mapper.updateEntityFromDto(request, entity);
        return mapper.toDto(repo.save(entity));
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new RuntimeException("Seller not found: " + id);
        repo.deleteById(id);
    }

    public Seller requireEntity(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Seller not found: " + id));
    }
}
