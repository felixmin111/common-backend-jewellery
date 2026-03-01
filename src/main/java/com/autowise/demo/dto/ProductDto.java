package com.autowise.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name must be at most 50 characters")
    private String name;

    @Size(max = 1000, message = "Code must be at most 1000 characters")
    private String code;

    @Size(max = 20, message = "Stock status must be at most 20 characters")
    private String stockStatus;

    @Size(max = 300, message = "Desc must be at most 300 characters")
    private String desc;

    private Long qty;

    private Float finalPrice;

    @Size(max = 50, message = "Collection must be at most 50 characters")
    private String collection;

    @Size(max = 100, message = "Short desc must be at most 100 characters")
    private String shortDesc;

    @Size(max = 60, message = "Color must be at most 60 characters")
    private String color;

    private List<ProductImageDto> productImages;

    private Float weight;
    private Float metarialLoss;
    private Float makingCost;
    private Long colorCount;

    @NotNull(message = "Depreciation is required")
    private Float depreciation;

    private Long productTypeId;

    // ✅ UI sections
    private Set<ProductGoldItemDto> productGolds;
    private Set<ProductJewelleryItemDto> productJewellerys;
}
