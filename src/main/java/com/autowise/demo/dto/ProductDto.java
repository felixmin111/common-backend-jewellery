package com.autowise.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;

    @NotBlank(message = "Product name is required")
    @Size(max = 50, message = "Product name must be at most 50 characters")
    private String name;

    @Size(max = 1000, message = "Code must be at most 1000 characters")
    private String code;

    @Size(max = 20, message = "Stock status must be at most 20 characters")
    private String stockStatus;

    @Size(max = 300, message = "Description must be at most 300 characters")
    private String desc;

    @Min(value = 0, message = "Qty must be >= 0")
    private Integer qty;

    @Size(max = 50, message = "Collection must be at most 50 characters")
    private String collection;

    @Size(max = 100, message = "Short description must be at most 100 characters")
    private String shortDesc;

    @Size(max = 100, message = "Color must be at most 100 characters")
    private String color;

    private Float weight;

    private Float metarialLoss;

    private Float makingCost;

    private Integer colorCount;

    @NotNull(message = "Product Type ID is required")
    private Long productTypeId;
}
