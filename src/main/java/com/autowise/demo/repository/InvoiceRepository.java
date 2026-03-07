package com.autowise.demo.repository;

import com.autowise.demo.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query("""
      select coalesce(sum(i.finalPrice), 0)
      from Invoice i
      where i.status = 'CONFIRMED'
        and i.createdAt >= :start
        and i.createdAt < :end
    """)
    BigDecimal sumConfirmedSalesBetween(LocalDateTime start, LocalDateTime end);

    @Query("""
      select count(i)
      from Invoice i
      where i.status = 'CONFIRMED'
        and i.createdAt >= :start
        and i.createdAt < :end
    """)
    long countConfirmedBetween(LocalDateTime start, LocalDateTime end);

    // Sales trend: group by day
    @Query("""
      select function('date', i.createdAt), coalesce(sum(i.finalPrice), 0)
      from Invoice i
      where i.status = 'CONFIRMED'
        and i.createdAt >= :start
        and i.createdAt < :end
      group by function('date', i.createdAt)
      order by function('date', i.createdAt)
    """)
    List<Object[]> dailySalesBetween(LocalDateTime start, LocalDateTime end);
}