package com.autowise.demo.model;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "description", length = 300)
    private String desc;


    private Integer qty;

    @Column(length = 50)
    private String collection;

    @Column(name = "short_desc", length = 100)
    private String shortDesc;

    @Column(length = 100)
    private String color;

    private Float weight;

    @Column(name = "metarial_loss")
    private Float metarialLoss;

    @Column(name = "making_cost")
    private Float makingCost;

    @Column(name = "color_count")
    private Integer colorCount;

    @Column(name = "product_type_id", nullable = false)
    private Long productTypeId;
}
