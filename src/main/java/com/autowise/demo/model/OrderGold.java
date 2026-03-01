package com.autowise.demo.model;
import com.autowise.demo.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_gold")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderGold {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    @Column(name = "gold_source_id", nullable = false)
    private Long goldSourceId;

    @Column(name = "craft_id", nullable = false)
    private Long craftId;

    // weight per 1 piece
    @Column(name = "weight_per_unit", nullable = false)
    private Double weightPerUnit;

    // calculated: weightPerUnit * orderItem.qty
    @Column(name = "total_weight", nullable = false)
    private Double totalWeight;
}