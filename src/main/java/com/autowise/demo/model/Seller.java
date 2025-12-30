package com.autowise.demo.model;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(length = 30)
    private String phone;

    @Column(length = 120)
    private String email;

    @Column(length = 200)
    private String address;
}
