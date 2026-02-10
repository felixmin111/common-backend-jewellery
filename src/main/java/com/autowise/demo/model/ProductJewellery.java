package com.autowise.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_jewellery")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductJewellery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ relation to product
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // ✅ relation to gems_package
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gems_package_id", nullable = false)
    private GemsPackage gemsPackage;

    @Column(nullable = false)
    private Integer qty;

    @Column(name = "selling_price", nullable = false)
    private Double sellingPrice;
}
