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
      SELECT
        gp.id AS id,
        gp.name AS name,
        gp.package_number AS packageNumber,
        gp.gems_size AS gemsSize,
        gp.gems_weight AS gemsWeight,
        gp.quantity AS quantity,
        gp.unit_price AS unitPrice,
        gp.total_price AS totalPrice,
        gp.buy_date AS buyDate,
        gp.original_price AS originalPrice,
        gp.color AS color,
        gp.cutting AS cutting,
        gp.description AS description,

        gp.gem_type_id AS gemTypeId,
        gt.name AS gemTypeName,

        gp.seller_id AS sellerId,
        s.name AS sellerName,

        (gp.quantity - COALESCE(SUM(pj.qty), 0)) AS remainingQty
      FROM gems_package gp
      LEFT JOIN product_jewellery pj ON pj.gems_package_id = gp.id
      LEFT JOIN gem_type gt ON gt.id = gp.gem_type_id
      LEFT JOIN seller s ON s.id = gp.seller_id
      GROUP BY
        gp.id, gp.name, gp.package_number, gp.gems_size, gp.gems_weight,
        gp.quantity, gp.unit_price, gp.total_price, gp.buy_date, gp.original_price,
        gp.color, gp.cutting, gp.description,
        gp.gem_type_id, gt.name,
        gp.seller_id, s.name
    """, nativeQuery = true)
    List<Map<String, Object>> findAllWithRemainingQty();
}