package com.autowise.demo.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorItemDto {

    private Long id;

    @NotNull(message = "purchaseItemId is required")
    private Long purchaseItemId;

    @NotNull(message = "productId is required")
    private Long productId;

    @NotNull(message = "qty is required")
    private Long qty;

    @NotNull(message = "sellingPrice is required")
    private BigDecimal sellingPrice;

    private BigDecimal deductionAmount;

    @NotNull(message = "finalBuybackPrice is required")
    private BigDecimal finalBuybackPrice;
}