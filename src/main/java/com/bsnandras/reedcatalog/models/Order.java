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
@Table(name = "orders")
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

    @OneToMany(mappedBy = "order")
    private Set<Reed> reedList;

    @OneToMany(mappedBy = "order")
    private Set<Log> logs;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
