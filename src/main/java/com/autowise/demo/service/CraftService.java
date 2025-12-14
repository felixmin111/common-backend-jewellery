package com.autowise.demo.service;

import com.autowise.demo.dto.CraftDto;
import com.autowise.demo.mapper.CraftMapper;
import com.autowise.demo.model.Craft;
import com.autowise.demo.repository.CraftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CraftService {

    private final CraftMapper craftMapper;
    private final CraftRepository craftRepository;

    public List<CraftDto> getAll() {
        return craftRepository.findAll()
                .stream()
                .map(craftMapper::toDto)
                .toList();
    }

    public CraftDto getById(Long id) {
        Craft craft = craftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Craft not found: " + id));
        return craftMapper.toDto(craft);
    }

    public CraftDto create(CraftDto request) {
        Craft craft = craftMapper.toEntity(request);
        Craft saved = craftRepository.save(craft);
        return craftMapper.toDto(saved);
    }

    // ✅ FIXED UPDATE
    public CraftDto update(Long id, CraftDto request) {
        Craft craft = craftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Craft not found: " + id));

        craftMapper.updateEntityFromDto(request, craft);

        Craft saved = craftRepository.save(craft);
        return craftMapper.toDto(saved);
    }

    public void delete(Long id) {
        if (!craftRepository.existsById(id)) {
            throw new RuntimeException("Craft not found: " + id);
        }
        craftRepository.deleteById(id);
    }
}
