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

    private String normalizeDuplicate(String value) {
        if (value == null) return null;

        value = value.trim();

        int len = value.length();
        if (len % 2 == 0) {
            String half = value.substring(0, len / 2);
            if ((half + half).equals(value)) {
                return half;
            }
        }
        return value;
    }

    public CraftDto create(CraftDto request) {
        request.setNrc(normalizeDuplicate(request.getNrc()));
        request.setPhone(normalizeDuplicate(request.getPhone()));

        if (craftRepository.existsByNrc(request.getNrc())) {
            throw new RuntimeException("Craft with this NRC already exists: " + request.getNrc());
        }
        if (craftRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Craft with this phone already exists: " + request.getPhone());
        }

        Craft craft = craftMapper.toEntity(request);
        Craft saved = craftRepository.save(craft);
        return craftMapper.toDto(saved);
    }


    public CraftDto update(Long id, CraftDto request) {
        Craft craft = craftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Craft not found: " + id));

        // 🔒 normalize before mapping
        request.setNrc(normalizeDuplicate(request.getNrc()));
        request.setPhone(normalizeDuplicate(request.getPhone()));

        System.out.println("DEBUG before update (db) nrc=" + craft.getNrc() + " phone=" + craft.getPhone());
        System.out.println("DEBUG incoming (update) nrc=" + request.getNrc() + " phone=" + request.getPhone());

        craftMapper.updateEntityFromDto(request, craft);

        System.out.println("DEBUG after mapper (update) nrc=" + craft.getNrc() + " phone=" + craft.getPhone());

        Craft saved = craftRepository.save(craft);

        System.out.println("DEBUG saved (update) nrc=" + saved.getNrc() + " phone=" + saved.getPhone());

        return craftMapper.toDto(saved);
    }

    public void delete(Long id) {
        if (!craftRepository.existsById(id)) {
            throw new RuntimeException("Craft not found: " + id);
        }
        craftRepository.deleteById(id);
    }
}
