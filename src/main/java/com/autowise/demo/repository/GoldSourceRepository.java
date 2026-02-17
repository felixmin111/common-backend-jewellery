package com.autowise.demo.repository;

import com.autowise.demo.model.GoldSource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoldSourceRepository extends JpaRepository<GoldSource, Long> {
    boolean existsByName(String name);
}