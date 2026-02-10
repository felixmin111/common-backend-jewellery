package com.autowise.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductJewelleryItemDto {

    private Long id;

    @NotNull(message = "Gems Package ID is required")
    private Long gemsPackageId;

    @NotNull(message = "Qty is required")
    private Integer qty;

    @NotNull(message = "Selling price is required")
    private Double sellingPrice;

    // display only (response)
    private String gemsPackageName;
    private Double originalPrice;
    private Double unitWeight;
}
