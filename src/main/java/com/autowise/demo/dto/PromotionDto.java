package com.autowise.demo.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDto {

    private Long id;

    @NotBlank(message = "Promotion name is required")
    @Size(max = 200, message = "Promotion name must be at most 200 characters")
    private String name;

    @NotNull(message = "Discount rate is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Discount rate must be >= 0")
    @DecimalMax(value = "100.0", inclusive = true, message = "Discount rate must be <= 100")
    private Double discountRate;

    private String description; // optional

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;


    private String status; // "ACTIVE" / "INACTIVE"

    // output only
    private Instant createdAt;
    private Instant updatedAt;
}