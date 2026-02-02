package com.autowise.demo.service;

import com.autowise.demo.dto.GoldSourceDto;
import com.autowise.demo.mapper.GoldSourceMapper;
import com.autowise.demo.model.GoldSource;
import com.autowise.demo.repository.GoldSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GoldSourceService {

    private final GoldSourceRepository repo;
    private final GoldSourceMapper mapper;

    public List<GoldSourceDto> getAll() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    public GoldSourceDto getById(Long id) {
        GoldSource x = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("GoldSource not found: " + id));
        return mapper.toDto(x);
    }

    public GoldSourceDto create(GoldSourceDto request) {
        if (repo.existsByName(request.getName().trim())) {
            throw new RuntimeException("GoldSource name already exists: " + request.getName());
        }
        GoldSource saved = repo.save(mapper.toEntity(request));
        return mapper.toDto(saved);
    }

    public GoldSourceDto update(Long id, GoldSourceDto request) {
        GoldSource existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("GoldSource not found: " + id));

        mapper.updateEntityFromDto(request, existing);

        GoldSource saved = repo.save(existing);
        return mapper.toDto(saved);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new RuntimeException("GoldSource not found: " + id);
        repo.deleteById(id);
    }
}
