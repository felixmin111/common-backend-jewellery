package com.autowise.demo.repository;

import com.autowise.demo.model.GemType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GemTypeRepository extends JpaRepository<GemType, Long> {
    boolean existsByNameIgnoreCase(String name);
}
