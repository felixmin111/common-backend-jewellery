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
public class ProductImageDto {

    private Long id;

    @NotBlank(message = "Image URL is required")
    @Size(max = 1000, message = "Image URL must be at most 1000 characters")
    private String imageUrl;


}