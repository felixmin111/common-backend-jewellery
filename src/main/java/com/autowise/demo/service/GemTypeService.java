package com.autowise.demo.service;

import com.autowise.demo.dto.GemTypeDto;
import com.autowise.demo.mapper.GemTypeMapper;
import com.autowise.demo.model.GemType;
import com.autowise.demo.repository.GemTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GemTypeService {

    private final GemTypeRepository repo;
    private final GemTypeMapper mapper;

    public List<GemTypeDto> getAll() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    public GemTypeDto getById(Long id) {
        return mapper.toDto(requireEntity(id));
    }

    public GemTypeDto create(GemTypeDto req) {
        if (repo.existsByNameIgnoreCase(req.getName())) {
            throw new RuntimeException("Gem type already exists: " + req.getName());
        }
        GemType saved = repo.save(mapper.toEntity(req));
        return mapper.toDto(saved);
    }

    public GemTypeDto update(Long id, GemTypeDto req) {
        GemType entity = requireEntity(id);
        mapper.updateEntityFromDto(req, entity);
        return mapper.toDto(repo.save(entity));
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new RuntimeException("Gem type not found: " + id);
        repo.deleteById(id);
    }

    public GemType requireEntity(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Gem type not found: " + id));
    }
}
