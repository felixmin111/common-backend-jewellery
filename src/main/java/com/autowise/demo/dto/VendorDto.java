package com.autowise.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class VendorDto {

    private Long id;

    private BigDecimal buybackBasePrice;

    private BigDecimal deductionRate;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description must be at most 500 characters")
    private String desc;

    private BigDecimal buybackPrice;

    private LocalDate buybackDate;

    @NotNull(message = "customerId is required")
    private Long customerId;

    @NotNull(message = "goldPriceId is required")
    private Long goldPriceId;

    private BigDecimal deductionAmount;
}