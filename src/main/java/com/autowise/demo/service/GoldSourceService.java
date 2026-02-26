package com.autowise.demo.service;

import com.autowise.demo.dto.GoldSourceDto;
import com.autowise.demo.mapper.GoldSourceMapper;
import com.autowise.demo.model.GoldSource;
import com.autowise.demo.repository.GoldSourceRepository;
import com.autowise.demo.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GoldSourceService {

    private final GoldSourceRepository repo;
    private final SellerRepository sellerRepo;
    private final GoldSourceMapper mapper;

    public List<GoldSourceDto> getAll() {

        return repo.findAllWithUsedWeight()
                .stream()
                .map(r -> {
                    Long id = (Long) r[0];
                    String name = (String) r[1];
                    String purity = (String) r[2];
                    Float total = (Float) r[3];
                    Float originalPrice = (Float) r[4];
                    String color = (String) r[5];
                    String country = (String) r[6];
                    Long sellerId = (Long) r[7];
                    Float used = ((Number) r[8]).floatValue();

                    float remaining = (total == null ? 0f : total) - (used == null ? 0f : used);
                    if (remaining < 0) remaining = 0;

                    GoldSourceDto dto = new GoldSourceDto();
                    dto.setId(id);
                    dto.setName(name);
                    dto.setGoldPurity(purity);
                    dto.setWeight(total);
                    dto.setOriginalPrice(originalPrice);
                    dto.setColor(color);
                    dto.setSourceCountry(country);
                    dto.setSellerId(sellerId);

                    dto.setUsedWeight(used);
                    dto.setRemainingWeight(remaining);

                    return dto;
                })
                .toList();
    }

    public GoldSourceDto getById(Long id) {
        GoldSource x = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "GoldSource not found: " + id));
        return mapper.toDto(x);
    }

    public GoldSourceDto create(GoldSourceDto request) {
        String name = request.getName() == null ? "" : request.getName().trim();
        if (name.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is required");
        }
        if (repo.existsByName(name)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "GoldSource name already exists: " + name);
        }

        // ✅ sellerId must exist if provided
        if (request.getSellerId() != null && !sellerRepo.existsById(request.getSellerId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Seller not found: " + request.getSellerId());
        }

        // normalize name
        request.setName(name);

        GoldSource saved = repo.save(mapper.toEntity(request));
        return mapper.toDto(saved);
    }

    public GoldSourceDto update(Long id, GoldSourceDto request) {
        GoldSource existing = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "GoldSource not found: " + id));

        // ✅ sellerId must exist if provided
        if (request.getSellerId() != null && !sellerRepo.existsById(request.getSellerId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Seller not found: " + request.getSellerId());
        }

        // optional: keep name uniqueness on update
        if (request.getName() != null) {
            String newName = request.getName().trim();
            if (!newName.isBlank() && !newName.equals(existing.getName())) {
                if (repo.existsByName(newName)) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "GoldSource name already exists: " + newName);
                }
            }
            request.setName(newName);
        }

        mapper.updateEntityFromDto(request, existing);
        GoldSource saved = repo.save(existing);
        return mapper.toDto(saved);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "GoldSource not found: " + id);
        }
        repo.deleteById(id);
    }


}
