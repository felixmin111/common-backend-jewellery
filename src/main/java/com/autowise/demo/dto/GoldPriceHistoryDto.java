package com.autowise.demo.dto;

import com.autowise.demo.model.enums.GoldPriceStatus;
import com.autowise.demo.model.enums.GoldPurity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class GoldPriceHistoryDto {

    private Long id;
    private LocalDate recordDate;
    private GoldPurity purity;
    private BigDecimal buyPrice;
    private BigDecimal sellPrice;
    private GoldPriceStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}