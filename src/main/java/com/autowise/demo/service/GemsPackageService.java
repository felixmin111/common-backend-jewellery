package com.autowise.demo.service;

import com.autowise.demo.dto.CertificateImageDto;
import com.autowise.demo.dto.GemsPackageDto;
import com.autowise.demo.mapper.GemsPackageMapper;
import com.autowise.demo.model.CertificateImage;
import com.autowise.demo.model.GemsPackage;
import com.autowise.demo.repository.CertificateImageRepository;
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
    private final CertificateImageRepository certificateImageRepo;
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

        // ✅ 1. Check package number uniqueness
        if (request.getPackageNumber() != null &&
                repo.existsByPackageNumber(request.getPackageNumber())) {
            throw new IllegalArgumentException(
                    "Package number already exists: " + request.getPackageNumber()
            );
        }

        // ✅ 2. Map DTO → Entity (manual totalPrice preserved)
        GemsPackage entity = mapper.toEntity(request);

        // ✅ 3. Required relations
        entity.setGemType(gemTypeService.requireEntity(request.getGemTypeId()));

        // ✅ 4. Seller (optional)
        if (request.getSellerId() != null) {
            var seller = sellerService.requireEntity(request.getSellerId());
            entity.setSellerId(seller.getId());
            entity.setSellerName(seller.getName());
        }

        // ✅ 5. Auto-calc ONLY if user did NOT input totalPrice
        if (request.getTotalPrice() == null &&
                entity.getQuantity() != null &&
                entity.getUnitPrice() != null) {
            entity.setTotalPrice(entity.getQuantity() * entity.getUnitPrice());
        }

        GemsPackage saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    public GemsPackageDto update(Long id, GemsPackageDto request) {

        GemsPackage entity = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("GemsPackage not found: " + id));

        // ✅ 1. Package number uniqueness (ignore itself)
        if (request.getPackageNumber() != null &&
                repo.existsByPackageNumberAndIdNot(request.getPackageNumber(), id)) {
            throw new IllegalArgumentException(
                    "Package number already exists: " + request.getPackageNumber()
            );
        }

        // ✅ 2. Update normal fields (manual totalPrice included)
        mapper.updateEntityFromDto(request, entity);

        // ✅ 3. Required gem type
        entity.setGemType(gemTypeService.requireEntity(request.getGemTypeId()));

        // ✅ 4. Seller update / clear
        if (request.getSellerId() != null) {
            var seller = sellerService.requireEntity(request.getSellerId());
            entity.setSellerId(seller.getId());
            entity.setSellerName(seller.getName());
        } else {
            entity.setSellerId(null);
            entity.setSellerName(null);
        }

        // ✅ 5. Auto-calc ONLY if user did NOT input totalPrice
        if (request.getTotalPrice() == null &&
                entity.getQuantity() != null &&
                entity.getUnitPrice() != null) {
            entity.setTotalPrice(entity.getQuantity() * entity.getUnitPrice());
        }

        GemsPackage saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new RuntimeException("GemsPackage not found: " + id);
        repo.deleteById(id);
    }
    public List<GemsPackageDto> getAvailable() {
        return repo.findByCurrentQuantityGreaterThanAndCurrentWeightGreaterThan(0, 0.0)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public GemsPackageDto addCertificate(Long packageId, CertificateImageDto req) {
        GemsPackage pkg = repo.findById(packageId)
                .orElseThrow(() -> new RuntimeException("GemsPackage not found: " + packageId));

        CertificateImage cert = CertificateImage.builder()
                .imageUrl(req.getImageUrl())
                .title(req.getTitle())
                .gemsPackage(pkg)
                .build();

        pkg.getCertificateImages().add(cert); // cascade saves it

        return mapper.toDto(repo.save(pkg));
    }

    public void deleteCertificate(Long certId) {
        certificateImageRepo.deleteById(certId);
    }
}
