package com.autowise.demo.service;

import com.autowise.demo.dto.PromotionDto;
import com.autowise.demo.mapper.PromotionMapper;
import com.autowise.demo.model.Promotion;
import com.autowise.demo.model.enums.PromotionStatus;
import com.autowise.demo.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PromotionService {

    private final PromotionRepository repo;
    private final PromotionMapper mapper;

    public List<PromotionDto> getAll() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    public PromotionDto getById(Long id) {
        return mapper.toDto(requireEntity(id));
    }

    public PromotionDto create(PromotionDto req) {
        if (repo.existsByNameIgnoreCase(req.getName())) {
            throw new RuntimeException("Promotion name already exists: " + req.getName());
        }

        if (req.getEndDate().isBefore(req.getStartDate())) {
            throw new RuntimeException("End date must be after start date.");
        }

        Promotion entity = mapper.toEntity(req);
        entity.setStatus(parseStatus(req.getStatus()));

        Promotion saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    public PromotionDto update(Long id, PromotionDto req) {
        Promotion entity = requireEntity(id);

        if (req.getEndDate().isBefore(req.getStartDate())) {
            throw new RuntimeException("End date must be after start date.");
        }

        mapper.updateEntityFromDto(req, entity);
        entity.setStatus(parseStatus(req.getStatus()));

        Promotion saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new RuntimeException("Promotion not found: " + id);
        repo.deleteById(id);
    }

    public Promotion requireEntity(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion not found: " + id));
    }

    private PromotionStatus parseStatus(String status) {
        try {
            return PromotionStatus.valueOf(status);
        } catch (Exception e) {
            throw new RuntimeException("Invalid status: " + status + " (use ACTIVE or INACTIVE)");
        }
    }
}