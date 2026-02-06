package com.autowise.demo.repository;

import com.autowise.demo.model.GemsPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GemsPackageRepository extends JpaRepository<GemsPackage, Long> {
    boolean existsByPackageNumber(Long packageNumber);

    boolean existsByPackageNumberAndIdNot(Long packageNumber, Long id);
    List<GemsPackage> findByCurrentQuantityGreaterThanAndCurrentWeightGreaterThan(
            Integer qty, Double weight
    );
}
