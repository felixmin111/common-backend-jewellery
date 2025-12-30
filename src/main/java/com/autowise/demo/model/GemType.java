package com.autowise.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gem_type")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GemType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 120, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "gemType")
    @Builder.Default
    private List<GemsPackage> packages = new ArrayList<>();
}
