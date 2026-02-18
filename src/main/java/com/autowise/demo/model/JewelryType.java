package com.autowise.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "jewelry_types",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_type_name_per_category", columnNames = {"category_id", "name"})
        }
)
public class JewelryType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(name = "image_url", length = 1024)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "description", length = 2000)
    private String description;
}