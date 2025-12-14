package com.autowise.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CraftDto {

    private Long id;

    @NotBlank(message = "Shop name is required")
    @Size(max = 30, message = "Shop name must be at most 30 characters")
    private String shopName;

    @NotBlank(message = "NIC is required")
    @Size(max = 30, message = "NIC must be at most 30 characters")
    private String nic;

    @NotBlank(message = "Phone is required")
    @Size(max = 30, message = "Phone must be at most 30 characters")
    private String phone;

    @NotBlank(message = "Address is required")
    @Size(max = 100, message = "Address must be at most 100 characters")
    private String address;
}
