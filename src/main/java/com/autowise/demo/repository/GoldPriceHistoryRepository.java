package com.autowise.demo.repository;

import com.autowise.demo.model.GoldPriceHistory;
import com.autowise.demo.model.enums.GoldPriceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoldPriceHistoryRepository
        extends JpaRepository<GoldPriceHistory, Long> {

    Optional<GoldPriceHistory> findByStatus(GoldPriceStatus status);

    List<GoldPriceHistory> findAllByOrderByRecordDateDesc();
}