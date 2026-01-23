// src/main/java/com/autowise/demo/dto/ProductTagDto.java
package com.autowise.demo.dto;

import lombok.Data;

@Data
public class ProductTagDto {
    private Long id;          // used in response
    private String name;      // used in request + response
    private String description;
}
