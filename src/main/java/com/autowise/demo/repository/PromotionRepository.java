package com.autowise.demo.repository;

import com.autowise.demo.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    boolean existsByNameIgnoreCase(String name);
}