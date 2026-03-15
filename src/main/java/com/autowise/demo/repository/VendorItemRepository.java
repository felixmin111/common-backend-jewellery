package com.autowise.demo.repository;

import com.autowise.demo.model.VendorItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorItemRepository extends JpaRepository<VendorItem, Long> {
    List<VendorItem> findByVendorId(Long vendorId);
}