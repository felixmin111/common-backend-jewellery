package com.autowise.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDto {
    private Long id;
    private String name;
    private String phone;
    private String address;
    private String role;
    private String password;
}