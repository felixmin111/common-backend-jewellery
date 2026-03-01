package com.autowise.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "purchase")
public class PurchaseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="invoice_id", nullable=false)
    private Invoice invoice;

    @Column(name="product_id", nullable=false)
    private Long productId;

    @Column(name="qty", nullable=false)
    private Long qty;

    @Column(name="selling_price", nullable=false, precision=12, scale=2)
    private BigDecimal sellingPrice;

    @Column(name="subtotal", nullable=false, precision=12, scale=2)
    private BigDecimal subtotal;

    @Column(name="discount_amount", nullable=false, precision=12, scale=2)
    private BigDecimal discountAmount;

    @Column(name="final_price", nullable=false, precision=12, scale=2)
    private BigDecimal finalPrice;

    @Column(name="created_at", nullable=false)
    private LocalDateTime createdAt;

    @Column(name="updated_at", nullable=false)
    private LocalDateTime updatedAt;

    @PrePersist
    void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
        if (discountAmount == null) discountAmount = BigDecimal.ZERO;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = LocalDateTime.now();
    }


}