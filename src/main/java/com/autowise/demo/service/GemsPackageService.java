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

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class GemsPackageService {

    private final GemsPackageMapper mapper;
    private final GemsPackageRepository repo;
    private final CertificateImageRepository certificateImageRepo;
    private final SellerService sellerService;
    private final GemTypeService gemTypeService;

    public GemsPackageDto getById(Long id) {
        GemsPackage entity = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("GemsPackage not found: " + id));
        return mapper.toDto(entity);
    }

    public List<GemsPackageDto> getAll() {
        var rows = repo.findAllWithRemainingQty();
        return rows.stream().map(this::mapRowToDto).toList();
    }

    public GemsPackageDto create(GemsPackageDto request) {

        if (request.getPackageNumber() != null &&
                repo.existsByPackageNumber(request.getPackageNumber())) {
            throw new IllegalArgumentException("Package number already exists: " + request.getPackageNumber());
        }

        GemsPackage entity = mapper.toEntity(request);

        // required
        entity.setGemType(gemTypeService.requireEntity(request.getGemTypeId()));

        // seller
        if (request.getSellerId() != null) {
            var seller = sellerService.requireEntity(request.getSellerId());
            entity.setSellerId(seller.getId());
            entity.setSellerName(seller.getName());
        } else {
            entity.setSellerId(null);
            entity.setSellerName(null);
        }

        // auto calc total if null
        if (request.getTotalPrice() == null && entity.getQuantity() != null && entity.getUnitPrice() != null) {
            entity.setTotalPrice(entity.getQuantity() * entity.getUnitPrice());
        }

        GemsPackage saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    public GemsPackageDto update(Long id, GemsPackageDto request) {
        GemsPackage entity = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("GemsPackage not found: " + id));

        if (request.getPackageNumber() != null &&
                repo.existsByPackageNumberAndIdNot(request.getPackageNumber(), id)) {
            throw new IllegalArgumentException("Package number already exists: " + request.getPackageNumber());
        }

        mapper.updateEntityFromDto(request, entity);

        entity.setGemType(gemTypeService.requireEntity(request.getGemTypeId()));

        if (request.getSellerId() != null) {
            var seller = sellerService.requireEntity(request.getSellerId());
            entity.setSellerId(seller.getId());
            entity.setSellerName(seller.getName());
        } else {
            entity.setSellerId(null);
            entity.setSellerName(null);
        }

        if (request.getTotalPrice() == null && entity.getQuantity() != null && entity.getUnitPrice() != null) {
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

        certificateImageRepo.save(cert);

        return mapper.toDto(repo.findById(packageId).orElseThrow());
    }

    public void deleteCertificate(Long certId) {
        certificateImageRepo.deleteById(certId);
    }

    // ----------------------------
    // Map row -> DTO (FULL fields)
    // ----------------------------
    private GemsPackageDto mapRowToDto(Map<String, Object> r) {
        GemsPackageDto dto = new GemsPackageDto();

        dto.setId(toLongObj(r, "id"));
        dto.setName(toStr(r, "name"));

        dto.setPackageNumber(toLongObj(r, "packageNumber"));
        dto.setGemsSize(toDoubleObj(r, "gemsSize"));
        dto.setGemsWeight(toDoubleObj(r, "gemsWeight"));

        dto.setQuantity(toIntObj(r, "quantity"));
        dto.setUnitPrice(toDoubleObj(r, "unitPrice"));
        dto.setTotalPrice(toDoubleObj(r, "totalPrice"));

        // ✅ FIXED: return LocalDate instead of String
        dto.setBuyDate(toLocalDate(r, "buyDate"));

        dto.setOriginalPrice(toDoubleObj(r, "originalPrice"));
        dto.setColor(toStr(r, "color"));
        dto.setCutting(toStr(r, "cutting"));
        dto.setDescription(toStr(r, "description"));

        // ✅ FIXED: keep Long (no intValue)
        dto.setGemTypeId(toLongObj(r, "gemTypeId"));
        dto.setGemTypeName(toStr(r, "gemTypeName"));

        dto.setSellerId(toLongObj(r, "sellerId"));
        dto.setSellerName(toStr(r, "sellerName"));

        dto.setRemainingQty(toIntObj(r, "remainingQty"));

        dto.setCertificateImages(List.of());

        return dto;
    }
    private static LocalDate toLocalDate(Map<String, Object> r, String key) {
        Object v = get(r, key);
        if (v == null) return null;

        if (v instanceof java.sql.Date d) return d.toLocalDate();
        if (v instanceof LocalDate d) return d;

        return LocalDate.parse(v.toString());
    }

    private static Object get(Map<String, Object> r, String key) {
        Object v = r.get(key);
        if (v != null) return v;
        return r.get(key.toLowerCase());
    }

    private static Long toLongObj(Map<String, Object> r, String key) {
        Object v = get(r, key);
        return (v instanceof Number n) ? n.longValue() : null;
    }

    private static Integer toIntObj(Map<String, Object> r, String key) {
        Object v = get(r, key);
        return (v instanceof Number n) ? n.intValue() : null;
    }

    private static Double toDoubleObj(Map<String, Object> r, String key) {
        Object v = get(r, key);
        return (v instanceof Number n) ? n.doubleValue() : null;
    }

    private static String toStr(Map<String, Object> r, String key) {
        Object v = get(r, key);
        return v == null ? null : v.toString();
    }

    private static String toDateStr(Map<String, Object> r, String key) {
        Object v = get(r, key);
        if (v == null) return null;
        if (v instanceof java.sql.Date d) return d.toLocalDate().toString();
        if (v instanceof LocalDate d) return d.toString();
        return v.toString();
    }
}