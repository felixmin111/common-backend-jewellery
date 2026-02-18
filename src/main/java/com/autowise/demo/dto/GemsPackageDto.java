package com.autowise.demo.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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

    @NotNull(message = "Gems size is required")
    @PositiveOrZero(message = "Gems size must be 0 or positive")
    private Double gemsSize;

    @NotNull(message = "Gems weight is required")
    @PositiveOrZero(message = "Gems weight must be 0 or positive")
    private Double gemsWeight;

    @NotNull(message = " Color is required")
    @Size(max = 60, message = "Color must be at most 60 characters")
    private String color;

    @NotNull(message = "Cutting is required")
    @Size(max = 60, message = "Cutting must be at most 60 characters")
    private String cutting;

    private String description;

    @PositiveOrZero(message = "Original price must be 0 or positive")
    private Double originalPrice;

    @NotNull(message = "Buy date is required")
    private LocalDate buyDate;



    private List<CertificateImageDto> certificateImages;

    @NotNull(message = "Seller ID is required")
    private Long sellerId;

    @NotNull(message = "Seller name is required")
    @Size(max = 90, message = "Seller name must be at most 90 characters")
    private String sellerName;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    @NotNull(message = "Unit price is required")
    @PositiveOrZero(message = "Unit price must be 0 or positive")
    private Double unitPrice;

    @NotNull(message = "Total price is required")
    @PositiveOrZero(message = "Total price must be 0 or positive")
    private Double totalPrice;

    @NotNull(message = "Gem type is required")
    @Positive(message = "Gem type must be a valid id")
    private Long gemTypeId;

    private Integer currentQuantity;
    private Double currentWeight;

    // ✅ output only (display)
    private String gemTypeName;
}
