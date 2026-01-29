package com.autowise.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GemTypeDto {

    private Long id;

    @NotBlank(message = "Gem type name is required")
    @Size(max = 120, message = "Gem type name must be at most 120 characters")
    private String name;
}
