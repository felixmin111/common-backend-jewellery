package com.autowise.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="invoice_no", nullable=false, unique=true, length=30)
    private String invoiceNo;

    @Column(name="customer_id", nullable=false)
    private Long customerId;

    @Column(name="sub_total", nullable=false, precision=12, scale=2)
    private BigDecimal subTotal;

    @Column(name="discount_amount", nullable=false, precision=12, scale=2)
    private BigDecimal discountAmount;

    @Column(name="discount_percentage", precision=5, scale=2)
    private BigDecimal discountPercentage;

    @Column(name="final_price", nullable=false, precision=12, scale=2)
    private BigDecimal finalPrice;

    @Column(name="status", nullable=false, length=30)
    private String status;

    @Column(name="created_at", nullable=false)
    private LocalDateTime createdAt;

    @Column(name="updated_at", nullable=false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseItem> items = new ArrayList<>();

    @PrePersist
    void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
        if (discountAmount == null) discountAmount = BigDecimal.ZERO;
        if (status == null) status = "DRAFT";
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // getters/setters

}