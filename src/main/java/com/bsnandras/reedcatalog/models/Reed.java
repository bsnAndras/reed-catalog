package com.bsnandras.reedcatalog.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reeds") //TODO: Create Stock instead, which stores StockItems
public class Reed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReedStatus status;

    @Column(name = "sell_price", nullable = false)
    private int sellPrice;

    @ManyToOne
    @JoinColumn(name = "order_no")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;
}
