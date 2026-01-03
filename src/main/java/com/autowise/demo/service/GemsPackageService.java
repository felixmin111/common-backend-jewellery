package com.autowise.demo.service;

import com.autowise.demo.dto.GemsPackageDto;
import com.autowise.demo.mapper.GemsPackageMapper;
import com.autowise.demo.model.GemsPackage;
import com.autowise.demo.repository.GemsPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GemsPackageService {

    private final GemsPackageMapper mapper;
    private final GemsPackageRepository repo;
    private final SellerService sellerService;
    private final GemTypeService gemTypeService;

    public List<GemsPackageDto> getAll() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    public GemsPackageDto getById(Long id) {
        GemsPackage entity = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("GemsPackage not found: " + id));
        return mapper.toDto(entity);
    }

    public GemsPackageDto create(GemsPackageDto request) {
        GemsPackage entity = mapper.toEntity(request);
        entity.setGemType(gemTypeService.requireEntity(request.getGemTypeId()));
        if (request.getSellerId() != null) {
            var seller = sellerService.requireEntity(request.getSellerId());
            entity.setSellerId(seller.getId());
            entity.setSellerName(seller.getName());
        }

        GemsPackage saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    public GemsPackageDto update(Long id, GemsPackageDto request) {
        GemsPackage entity = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("GemsPackage not found: " + id));

        mapper.updateEntityFromDto(request, entity);
        entity.setGemType(gemTypeService.requireEntity(request.getGemTypeId()));

        GemsPackage saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new RuntimeException("GemsPackage not found: " + id);
        repo.deleteById(id);
    }
}
