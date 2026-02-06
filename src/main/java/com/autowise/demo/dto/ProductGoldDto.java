package com.autowise.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductGoldDto {

    private Long id;

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Gold Source ID is required")
    private Long goldSourceId;

    @NotNull(message = "Craft ID is required")
    private Long craftId;

    @NotNull(message = "Weight is required")
    private Float weight;

    @NotNull(message = "Gold purity is required")
    private Float goldPurity;

    // ✅ display only (for frontend table)
    private String productName;
    private String goldSourceName;
    private String craftShopName;
}
