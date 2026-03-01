package com.autowise.demo.service;

import com.autowise.demo.dto.PromotionDto;
import com.autowise.demo.mapper.PromotionMapper;
import com.autowise.demo.model.Promotion;
import com.autowise.demo.model.enums.PromotionStatus;
import com.autowise.demo.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PromotionService {

    private final PromotionRepository repo;
    private final PromotionMapper mapper;

    public List<PromotionDto> getAll() {
        return repo.findAll()
                .stream()
                .map(this::toDtoWithComputedStatus)
                .toList();
    }
    public PromotionDto getById(Long id) {
        return toDtoWithComputedStatus(requireEntity(id));
    }

    public PromotionDto create(PromotionDto req) {
        if (repo.existsByNameIgnoreCase(req.getName())) {
            throw new RuntimeException("Promotion name already exists: " + req.getName());
        }

        if (req.getEndDate().isBefore(req.getStartDate())) {
            throw new RuntimeException("End date must be after start date.");
        }

        Promotion entity = mapper.toEntity(req);
        entity.setStatus(computedStatus(entity));

        Promotion saved = repo.save(entity);
        return toDtoWithComputedStatus(saved);
    }

    public PromotionDto update(Long id, PromotionDto req) {
        Promotion entity = requireEntity(id);

        if (req.getEndDate().isBefore(req.getStartDate())) {
            throw new RuntimeException("End date must be after start date.");
        }

        mapper.updateEntityFromDto(req, entity);
        entity.setStatus(computedStatus(entity));

        Promotion saved = repo.save(entity);
        return toDtoWithComputedStatus(saved);
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
    private PromotionStatus computedStatus(Promotion p) {
        LocalDate today = LocalDate.now();

        if (p.getEndDate() != null && today.isAfter(p.getEndDate())) {
            return PromotionStatus.INACTIVE;
        }

        // not started yet → treat as INACTIVE for now
        if (p.getStartDate() != null && today.isBefore(p.getStartDate())) {
            return PromotionStatus.INACTIVE;
        }

        return PromotionStatus.ACTIVE;
    }
    private PromotionDto toDtoWithComputedStatus(Promotion p) {
        PromotionDto dto = mapper.toDto(p);
        dto.setStatus(computedStatus(p).name());
        return dto;
    }
}