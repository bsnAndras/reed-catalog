package com.bsnandras.reedcatalog.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
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

    /**
     * The date and time of the purchase (in UTC).
     */
    @Column(name = "date_of_purchase", nullable = false)
    @Builder.Default
    private Instant dateOfPurchase = Instant.now();

    @Column(nullable = false)
    private int totalPrice;

    @Column(name = "amount_to_pay", nullable = false)
    private int amountToPay;

    @Column(name = "notes")
    private String notes;

    @OneToMany(mappedBy = "order")
    private Set<Reed> reedList;

    @OneToMany(mappedBy = "order")
    private Set<Log> logs;

    @ManyToOne
    @JoinColumn(name = "partner_id", nullable = false)
    private Partner partner;
}
