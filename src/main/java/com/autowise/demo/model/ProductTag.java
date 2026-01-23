// src/main/java/com/autowise/demo/model/ProductTag.java
package com.autowise.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 255)
    private String description;
}
