package com.autowise.demo.repository;

import com.autowise.demo.model.GoldSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GoldSourceRepository extends JpaRepository<GoldSource, Long> {
    boolean existsByName(String name);

    @Query("select coalesce(sum(pg.weight),0) from ProductGold pg where pg.goldSource.id = :goldSourceId")
    double sumUsedByGoldSource(@Param("goldSourceId") Long goldSourceId);

    @Query("""
  select coalesce(sum(pg.weight),0)
  from ProductGold pg
  where pg.goldSource.id = :goldSourceId
    and pg.product.id = :productId
""")
    double sumUsedByGoldSourceAndProduct(@Param("goldSourceId") Long goldSourceId,
                                         @Param("productId") Long productId);
    @Query("""
      select gs.id,
             gs.name,
             gs.goldPurity,
             gs.weight,
             gs.originalPrice,
             gs.color,
             gs.sourceCountry,
             gs.sellerId,
             coalesce(sum(pg.weight), 0)
      from GoldSource gs
      left join gs.productGolds pg
      group by gs.id, gs.name, gs.goldPurity, gs.weight, gs.originalPrice, gs.color, gs.sourceCountry, gs.sellerId
    """)
    List<Object[]> findAllWithUsedWeight();

}