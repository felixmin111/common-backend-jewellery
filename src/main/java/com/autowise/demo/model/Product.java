package com.autowise.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

    // ✅ KEEP THIS (your existing field)
    @Column(name = "product_type_id")
    private Long productTypeId;

    @Column(name = "reference_price", precision = 15, scale = 2)
    private BigDecimal referencePrice;

    @Column(precision = 15, scale = 2,name = "final_price")
    private BigDecimal finalPrice;

    // ✅ NEW: read-only relation using SAME column (doesn't break your code)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    private JewelryType productType;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> productImages = new ArrayList<>();

    // ✅ Product -> Gold rows
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<ProductGold> productGolds = new LinkedHashSet<>();

    // ✅ Product -> Jewellery rows
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<ProductJewellery> productJewellerys = new LinkedHashSet<>();

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
