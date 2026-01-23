package com.autowise.demo.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    // ⚠️ If you use UserDto as a response too, remove @NotBlank here
    private String password;

    @Column(unique = true)
    private String nrc;
    @Column(nullable = false, unique = true)
    private String phone;
    private String address;

    private String role;   // ✅ ADD THIS
    private String token;
}
