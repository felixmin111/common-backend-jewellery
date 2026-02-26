package com.autowise.demo.repository;

import com.autowise.demo.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByPhone(String phone);
}
