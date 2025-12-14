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

    @Column(name="shop_name", length = 30, nullable = false)
    private String shopName;

    @Column(length = 30, nullable = false)
    private String nic;

    @Column(length = 30, nullable = false)
    private String phone;

    @Column(length = 100, nullable = false)
    private String address;
}
