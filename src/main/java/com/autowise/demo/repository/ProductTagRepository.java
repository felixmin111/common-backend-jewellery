// src/main/java/com/autowise/demo/repository/ProductTagRepository.java
package com.autowise.demo.repository;

import com.autowise.demo.model.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {
    boolean existsByName(String name);
}
