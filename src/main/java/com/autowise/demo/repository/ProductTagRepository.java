package com.autowise.demo.repository;

import com.autowise.demo.model.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {
    Optional<ProductTag> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}
