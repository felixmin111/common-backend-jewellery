package com.autowise.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "vendors")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="buyback_base_price", precision = 12, scale = 2)
    private BigDecimal buybackBasePrice;

    @Column(name="deduction_rate", precision = 12, scale = 2)
    private BigDecimal deductionRate;

    @Column(name="\"desc\"", length = 500, nullable = false)
    private String desc;

    @Column(name="buyback_price", precision = 12, scale = 2)
    private BigDecimal buybackPrice;

    @Column(name="buyback_date")
    private LocalDate buybackDate;

    @Column(name="customer_id")
    private Long customerId;

    @Column(name="gold_price_id")
    private Long goldPriceId;

    @Column(name="deduction_amount", precision = 12, scale = 2)
    private BigDecimal deductionAmount;

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