package com.autowise.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "category")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 90, nullable = false)
    private String name;

    @Column(name = "description", length = 400)
    private String description;

    @Column(length = 100, nullable = false, unique = true)
    private String code;

//    @Column(name = "image_url", length = 100)
//    private String imageUrl;
}
