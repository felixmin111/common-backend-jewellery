package com.autowise.demo.service;

import com.autowise.demo.dto.GoldPriceHistoryDto;
import com.autowise.demo.mapper.GoldPriceHistoryMapper;
import com.autowise.demo.model.GoldPriceHistory;
import com.autowise.demo.model.enums.GoldPriceStatus;
import com.autowise.demo.repository.GoldPriceHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GoldPriceHistoryService {

    private final GoldPriceHistoryRepository repo;
    private final GoldPriceHistoryMapper mapper;

    // Get all (history)
    public List<GoldPriceHistoryDto> getAll() {
        return repo.findAllByOrderByRecordDateDesc()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    // Get current active price
    public GoldPriceHistoryDto getActive() {
        return repo.findByStatus(GoldPriceStatus.ACTIVE)
                .map(mapper::toDto)
                .orElseThrow(() ->
                        new RuntimeException("No active gold price found"));
    }

    // CREATE new gold price
    public GoldPriceHistoryDto create(GoldPriceHistoryDto dto) {

        // 1️⃣ Deactivate existing ACTIVE price
        repo.findByStatus(GoldPriceStatus.ACTIVE)
                .ifPresent(old -> old.setStatus(GoldPriceStatus.INACTIVE));

        // 2️⃣ Create new ACTIVE price
        GoldPriceHistory entity = mapper.toEntity(dto);
        entity.setStatus(GoldPriceStatus.ACTIVE);

        GoldPriceHistory saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    // UPDATE (usually not recommended, but allowed)
    public GoldPriceHistoryDto update(Long id, GoldPriceHistoryDto dto) {
        GoldPriceHistory entity = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Gold price not found"));

        mapper.updateEntityFromDto(dto, entity);

        return mapper.toDto(entity);
    }

    // DELETE (optional)
    public void delete(Long id) {
        repo.deleteById(id);
    }
}