package com.autowise.demo.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificateImageDto {
    private Long id;
    private String imageUrl;
    private String title;
}