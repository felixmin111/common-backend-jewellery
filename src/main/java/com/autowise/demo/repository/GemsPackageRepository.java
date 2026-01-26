package com.autowise.demo.repository;

import com.autowise.demo.model.GemsPackage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GemsPackageRepository extends JpaRepository<GemsPackage, Long> {
    boolean existsByPackageNumber(Long packageNumber);
    boolean existsByPackageNumberAndIdNot(Long packageNumber, Long id);
}
