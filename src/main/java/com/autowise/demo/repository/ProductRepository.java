package com.autowise.demo.repository;

import com.autowise.demo.model.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
        select distinct p from Product p
        left join fetch p.productType pt
        left join fetch p.productGolds pg
        left join fetch pg.goldSource
        left join fetch pg.craft
        left join fetch p.productJewellerys pj
        left join fetch pj.gemsPackage
        where p.id = :id
    """)
    Optional<Product> findByIdWithDetails(@Param("id") Long id);
}
