package com.autowise.demo.repository;

import com.autowise.demo.model.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {
    List<PurchaseItem> findByInvoice_Id(Long invoiceId);
}