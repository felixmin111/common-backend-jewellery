package com.autowise.demo.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GemsPackageDto {

    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 400, message = "Name must be at most 400 characters")
    private String name;

    @NotNull(message = "Package number is required")
    private Long packageNumber;

    @PositiveOrZero(message = "Gems size must be 0 or positive")
    private Double gemsSize;

    @PositiveOrZero(message = "Gems weight must be 0 or positive")
    private Double gemsWeight;

    @Size(max = 60, message = "Color must be at most 60 characters")
    private String color;

    @Size(max = 60, message = "Cutting must be at most 60 characters")
    private String cutting;

    private String description;

    @PositiveOrZero(message = "Original price must be 0 or positive")
    private Double originalPrice;

    private LocalDate buyDate;

    private Long certificateId;
    private Long sellerId;

    @Size(max = 90, message = "Seller name must be at most 90 characters")
    private String sellerName;

    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    @PositiveOrZero(message = "Unit price must be 0 or positive")
    private Double unitPrice;

    @PositiveOrZero(message = "Total price must be 0 or positive")
    private Double totalPrice;

    @NotNull(message = "Gem type is required")
    @Positive(message = "Gem type must be a valid id")
    private Long gemTypeId;

    // ✅ output only (display)
    private String gemTypeName;
}
