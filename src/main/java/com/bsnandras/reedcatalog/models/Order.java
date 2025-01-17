package com.bsnandras.reedcatalog.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Set;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_of_purchase", nullable = false)
    private Date dateOfPurchase;

    @Column(nullable = false)
    private int totalPrice;

    @Column(name = "amount_to_pay",nullable = false)
    private int amountToPay;

    @OneToMany
    @JoinColumn(name = "order_no",nullable = false)
    private Set<Reed> reedList;
}
