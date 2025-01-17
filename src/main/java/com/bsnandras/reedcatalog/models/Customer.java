package com.bsnandras.reedcatalog.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int balance;

    @OneToMany
    @JoinColumn(name = "customer_id", nullable = false)
    private Set<Order> orderList;
}
