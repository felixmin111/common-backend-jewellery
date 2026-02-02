package com.autowise.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoldSourceDto {

    private Long id;

    @Size(max = 40, message = "Gold purity must be at most 40 characters")
    private String goldPurity;

    private Float weight;

    @Size(max = 100, message = "Color must be at most 100 characters")
    private String color;

    @Size(max = 40, message = "Source country must be at most 40 characters")
    private String sourceCountry;

    private Float originalPrice;

    private Long sellerId;

    @NotBlank(message = "Name is required")
    @Size(max = 90, message = "Name must be at most 90 characters")
    private String name;
}
