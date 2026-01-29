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
public class SellerDto {

    private Long id;

    @NotBlank(message = "Seller name is required")
    @Size(max = 90, message = "Seller name must be at most 90 characters")
    private String name;

    @Size(max = 30, message = "Phone must be at most 30 characters")
    private String phone;

    @Size(max = 120, message = "Email must be at most 120 characters")
    private String email;

    @Size(max = 200, message = "Address must be at most 200 characters")
    private String address;
}
