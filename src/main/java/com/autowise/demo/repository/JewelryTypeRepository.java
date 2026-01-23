package com.autowise.demo.repository;

import com.autowise.demo.model.JewelryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JewelryTypeRepository extends JpaRepository<JewelryType, Long> {
    List<JewelryType> findAllByCategoryIdOrderByNameAsc(Long categoryId);
    boolean existsByCategoryIdAndNameIgnoreCase(Long categoryId, String name);
}