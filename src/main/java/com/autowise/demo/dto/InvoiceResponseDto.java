package com.autowise.demo.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceResponseDto {

    private Long id;
    private String invoiceNo;
    private Long customerId;

    private BigDecimal subTotal;
    private BigDecimal discountAmount;
    private BigDecimal discountPercentage;
    private BigDecimal finalPrice;

    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<ItemDto> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ItemDto {
        private Long id;
        private Long productId;
        private Long qty;
        private BigDecimal sellingPrice;
        private BigDecimal subtotal;
        private BigDecimal discountAmount;
        private BigDecimal finalPrice;
    }
}