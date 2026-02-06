package com.autowise.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 1000)
    private String code;

    @Column(name = "stock_status", length = 20)
    private String stockStatus;

    // ✅ Postgres keyword: use quoted column name
    @Column(name = "\"desc\"", length = 300)
    private String desc;

    private Long qty;

    @Column(length = 50)
    private String collection;

    @Column(name = "short_desc", length = 100)
    private String shortDesc;

    @Column(length = 60)
    private String color;

    private Float weight;

    @Column(name = "metarial_loss")
    private Float metarialLoss;

    @Column(name = "making_cost")
    private Float makingCost;

    @Column(name = "color_count")
    private Long colorCount;

    @Column(nullable = false)
    private Float depreciation;

    @Column(name = "product_type_id")
    private Long productTypeId;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
