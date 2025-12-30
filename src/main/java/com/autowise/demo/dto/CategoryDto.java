package com.autowise.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {

    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 90, message = "Name must be at most 90 characters")
    private String name;

    @Size(max = 400, message = "Description must be at most 400 characters")
    private String description;

    @NotBlank(message = "Code is required")
    @Size(max = 100, message = "Code must be at most 100 characters")
    private String code;

//    @Size(max = 100, message = "Image URL must be at most 100 characters")
//    private String imageUrl;
}
