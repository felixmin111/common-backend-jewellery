package com.autowise.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "gold_source")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoldSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gold_purity", length = 40)
    private String goldPurity;

    private Float weight;

    @Column(length = 100)
    private String color;

    @Column(name = "source_country", length = 40)
    private String sourceCountry;

    @Column(name = "original_price")
    private Float originalPrice;

    // ✅ keep FK column for DTO
    @Column(name = "seller_id")
    private Long sellerId;

    // ✅ real relation (read-only mapping to same column)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", insertable = false, updatable = false)
    private Seller seller;

    @Column(nullable = false, length = 90)
    private String name;

    // optional reverse relation to ProductGold
    @OneToMany(mappedBy = "goldSource")
    @Builder.Default
    private Set<ProductGold> productGolds = new LinkedHashSet<>();

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
