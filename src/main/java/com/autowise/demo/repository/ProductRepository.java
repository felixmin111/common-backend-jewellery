package com.autowise.demo.repository;

import com.autowise.demo.model.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    long countByQtyLessThanEqual(Long qty);

    List<Product> findTop10ByQtyLessThanEqualOrderByQtyAsc(Long qty);

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

    @Query("""
    select distinct p from Product p
    left join fetch p.productImages
    left join fetch p.productGolds pg
    left join fetch pg.goldSource
    left join fetch pg.craft
    left join fetch p.productJewellerys pj
    left join fetch pj.gemsPackage
    where p.productTypeId = :typeId
""")
    List<Product> findByProductTypeIdWithDetails(@Param("typeId") Long typeId);
    @Query("""
    select distinct p from Product p
    left join fetch p.productType pt
    left join fetch pt.category c
    left join fetch p.productImages
    left join fetch p.productGolds pg
    left join fetch pg.goldSource
    left join fetch pg.craft
    left join fetch p.productJewellerys pj
    left join fetch pj.gemsPackage
""")
    List<Product> findAllWithDetails();
}
