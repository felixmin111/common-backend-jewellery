package com.autowise.demo.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductTagDto {
    private Long id;
    private String name;
    private String description;
}
