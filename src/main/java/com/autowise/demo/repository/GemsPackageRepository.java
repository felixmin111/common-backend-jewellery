package com.autowise.demo.repository;

import com.autowise.demo.model.GemsPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface GemsPackageRepository extends JpaRepository<GemsPackage, Long> {
    boolean existsByPackageNumber(Long packageNumber);

    boolean existsByPackageNumberAndIdNot(Long packageNumber, Long id);
    List<GemsPackage> findByCurrentQuantityGreaterThanAndCurrentWeightGreaterThan(
            Integer qty, Double weight
    );
    @Query(value = """
  SELECT gp.id as id,
         gp.name as name,
         gp.quantity as quantity,
         gp.gems_size as gemsSize,
         gp.original_price as originalPrice,
         gt.name as gemTypeName,
         (gp.quantity - COALESCE(SUM(pj.qty), 0)) as remainingQty
  FROM gems_packages gp
  LEFT JOIN product_jewellery pj ON pj.gems_package_id = gp.id
  LEFT JOIN gem_types gt ON gt.id = gp.gem_type_id
  GROUP BY gp.id, gp.name, gp.quantity, gp.gems_size, gp.original_price, gt.name
""", nativeQuery = true)
    List<Map<String,Object>> findAllWithRemainingQty();
}
