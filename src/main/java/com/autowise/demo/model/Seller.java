package com.autowise.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "seller")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 90, nullable = false)
    private String name;

    @Column(length = 30, unique = true)
    private String phone;

    @Column(length = 120, unique = true)
    private String email;

    @Column(length = 200)
    private String address;

    // ✅ one seller -> many gold sources
    @OneToMany(mappedBy = "seller")
    @Builder.Default
    private Set<GoldSource> goldSources = new LinkedHashSet<>();
}
