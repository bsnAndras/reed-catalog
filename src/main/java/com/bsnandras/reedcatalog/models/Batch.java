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
public class Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false)
    private String batchName;

    @Column(name = "date_of_purchase", nullable = false)
    private Date date;

    @Column(nullable = false)
    private String maker;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    private String description;

    @OneToMany
    @JoinColumn(name = "batch_no",nullable = false)
    private Set<Reed> reedList;
}
