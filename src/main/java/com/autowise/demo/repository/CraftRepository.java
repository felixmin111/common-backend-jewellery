package com.autowise.demo.repository;

import com.autowise.demo.model.Craft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CraftRepository extends JpaRepository<Craft, Long> {}
