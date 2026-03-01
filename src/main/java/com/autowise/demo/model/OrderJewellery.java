package com.autowise.demo.model;
import com.autowise.demo.model.enums.OrderItemType;
import com.autowise.demo.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_jewellery")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderJewellery {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    @Column(name = "gems_package_id", nullable = false)
    private Long gemsPackageId;

    // qty per 1 piece
    @Column(name = "qty_per_unit", nullable = false)
    private Integer qtyPerUnit;

    // calculated: qtyPerUnit * orderItem.qty
    @Column(name = "total_qty", nullable = false)
    private Integer totalQty;
}