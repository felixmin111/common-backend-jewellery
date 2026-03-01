package com.autowise.demo.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseSaveRequestDto {

    private Long customerId;
    private String status; // DRAFT / CONFIRMED
    private BigDecimal discountAmount;
    private BigDecimal discountPercentage;
    private List<ItemDto> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ItemDto {
        private Long productId;
        private Long qty;
        private BigDecimal sellingPrice;
    }
}