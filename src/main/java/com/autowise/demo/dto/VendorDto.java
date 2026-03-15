package com.autowise.demo.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorDto {

    private Long id;

    @NotNull(message = "invoiceId is required")
    private Long invoiceId;

    @NotNull(message = "customerId is required")
    private Long customerId;

    private String invoiceNo;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description must be at most 500 characters")
    private String desc;

    private LocalDate buybackDate;

    private BigDecimal totalBuybackPrice;

    @NotEmpty(message = "Sell back items are required")
    private List<VendorItemDto> items;
}