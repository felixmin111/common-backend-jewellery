package com.autowise.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "craft")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Craft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shop_name", nullable = false)
    private String shopName;

    @Column(name = "nrc", nullable = false)
    private String nrc;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;
}
