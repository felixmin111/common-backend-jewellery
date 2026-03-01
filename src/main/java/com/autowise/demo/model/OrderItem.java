package com.autowise.demo.model;
import com.autowise.demo.model.enums.OrderItemType;
import com.autowise.demo.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "order_item")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderItemType type;

    // PRODUCT item
    @Column(name = "product_id")
    private Long productId;

    // Snapshots (keep history even if product changes later)
    @Column(name = "product_name", length = 120)
    private String productName;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(nullable = false)
    private Integer qty;

    @Column(name = "line_total")
    private Double lineTotal;

    // CUSTOM item materials
    @OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderGold> goldRows = new ArrayList<>();

    @OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderJewellery> jewelleryRows = new ArrayList<>();

    @Column(name = "custom_note", columnDefinition = "TEXT")
    private String customNote;
}