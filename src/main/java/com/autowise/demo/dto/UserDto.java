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

    @NotBlank
    private String password;

    @Column(unique = true)
    private String nrc;
    @Column(nullable = false, unique = true)
    private String phone;
    private String address;
    private String token;
}
