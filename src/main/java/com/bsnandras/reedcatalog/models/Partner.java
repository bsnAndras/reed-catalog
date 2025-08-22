package com.bsnandras.reedcatalog.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "partners")
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int balance;

    @OneToMany(mappedBy = "partner")
    private List<Order> orderList;

    @OneToMany(mappedBy = "fromPartner")
    private List<Transaction> sentTransactions;

    @OneToMany(mappedBy = "toPartner")
    private List<Transaction> receivedTransactions;
}
