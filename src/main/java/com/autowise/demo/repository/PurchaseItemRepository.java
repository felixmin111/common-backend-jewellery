package com.autowise.demo.repository;

import com.autowise.demo.model.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {

    List<PurchaseItem> findByInvoice_Id(Long invoiceId);

    @Query("""
      select pi.productId, coalesce(sum(pi.qty), 0)
      from PurchaseItem pi
      join pi.invoice inv
      where inv.status = 'CONFIRMED'
        and inv.createdAt >= :start
        and inv.createdAt < :end
      group by pi.productId
      order by coalesce(sum(pi.qty), 0) desc
    """)
    List<Object[]> topProductsByQty(LocalDateTime start, LocalDateTime end);
}