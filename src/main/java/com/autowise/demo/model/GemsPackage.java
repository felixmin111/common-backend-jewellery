package com.autowise.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "gems_package")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GemsPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 400, nullable = false)
    private String name;

    @Column(name = "package_number")
    private Long packageNumber;

    @Column(name = "gems_size")
    private Double gemsSize;

    @Column(name = "gems_weight")
    private Double gemsWeight;

    @Column(length = 60)
    private String color;

    @Column(length = 60)
    private String cutting;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "original_price")
    private Double originalPrice;

    @Column(name = "buy_date")
    private LocalDate buyDate;

    @Column(name = "certificate_id")
    private Long certificateId;

    @Column(name = "seller_id")
    private Long sellerId;

    @Column(name = "seller_name", length = 90)
    private String sellerName;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "total_price")
    private Double totalPrice;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gem_type_id", nullable = false)
    private GemType gemType;
}
