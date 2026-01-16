package com.autowise.demo.repository;

import com.autowise.demo.model.Craft;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CraftRepository extends JpaRepository<Craft, Long> {
    boolean existsByNrc(String nrc);
    boolean existsByPhone(String phone);
    Optional<Craft> findByNrc(String nrc);
    Optional<Craft> findByPhone(String phone);
}
