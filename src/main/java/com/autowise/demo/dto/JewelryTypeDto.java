package com.autowise.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record JewelryTypeDto(
        Long id,
        @NotBlank String name,
        @NotNull Long categoryId,
        String categoryName // helpful for UI (optional from client)
) {
}
