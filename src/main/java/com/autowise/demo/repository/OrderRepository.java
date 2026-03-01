package com.autowise.demo.repository;

import com.autowise.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
      select distinct o from Order o
      left join fetch o.items i
      left join fetch i.goldRows
      left join fetch i.jewelleryRows
      where o.id = :id
    """)
    Optional<Order> findByIdWithDetails(@Param("id") Long id);
}