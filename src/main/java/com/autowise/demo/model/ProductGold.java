package com.autowise.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_gold")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductGold {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ relation to product
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // ✅ relation to gold_source
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gold_source_id", nullable = false)
    private GoldSource goldSource;

    // ✅ relation to craft
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "craft_id", nullable = false)
    private Craft craft;

    @Column(nullable = false)
    private Float weight;

    @Column(name = "gold_purity", nullable = false)
    private Float goldPurity;
}
