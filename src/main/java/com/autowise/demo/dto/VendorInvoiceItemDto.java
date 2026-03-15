package com.autowise.demo.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorInvoiceItemDto {

    private Long purchaseItemId;
    private Long productId;
    private String productName;
    private Long qty;
    private BigDecimal sellingPrice;
    private BigDecimal finalPrice;
}