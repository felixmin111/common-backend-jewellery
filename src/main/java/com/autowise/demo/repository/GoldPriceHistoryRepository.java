package com.autowise.demo.repository;

import com.autowise.demo.model.GoldPriceHistory;
import com.autowise.demo.model.enums.GoldPriceStatus;
import com.autowise.demo.model.enums.GoldPurity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoldPriceHistoryRepository extends JpaRepository<GoldPriceHistory, Long> {

    Optional<GoldPriceHistory> findByPurityAndStatus(GoldPurity purity, GoldPriceStatus status);

    List<GoldPriceHistory> findAllByOrderByRecordDateDesc();

    @org.springframework.data.jpa.repository.Modifying
    @org.springframework.data.jpa.repository.Query("""
        update GoldPriceHistory g
           set g.status = com.autowise.demo.model.enums.GoldPriceStatus.INACTIVE
         where g.purity = :purity
           and g.status = com.autowise.demo.model.enums.GoldPriceStatus.ACTIVE
    """)
    int deactivateActiveByPurity(@org.springframework.data.repository.query.Param("purity") GoldPurity purity);

    @org.springframework.data.jpa.repository.Modifying
    @org.springframework.data.jpa.repository.Query("""
        update GoldPriceHistory g
           set g.status = com.autowise.demo.model.enums.GoldPriceStatus.INACTIVE
         where g.purity = :purity
           and g.status = com.autowise.demo.model.enums.GoldPriceStatus.ACTIVE
           and g.id <> :id
    """)
    int deactivateOtherActiveByPurity(
            @org.springframework.data.repository.query.Param("purity") GoldPurity purity,
            @org.springframework.data.repository.query.Param("id") Long id
    );
}