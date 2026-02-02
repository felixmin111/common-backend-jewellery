package com.autowise.demo.dto;

import com.autowise.demo.model.enums.CustomerStatus;
import com.autowise.demo.model.enums.CustomerType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 120, message = "Name must be at most 120 characters")
    private String name;

    @NotBlank(message = "Phone is required")
    @Size(max = 30, message = "Phone must be at most 30 characters")
    private String phone;

    @Email(message = "Email must be valid")
    @Size(max = 120, message = "Email must be at most 120 characters")
    private String email; // optional

    private String address; // optional

    @NotNull(message = "Customer type is required")
    private CustomerType customerType;

    @NotNull(message = "Status is required")
    private CustomerStatus status;

    // output-only (read)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}