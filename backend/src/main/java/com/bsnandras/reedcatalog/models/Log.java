package com.bsnandras.reedcatalog.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    @Id
    @Column(name = "date_time", nullable = false, unique = true, updatable = false)
    @Builder.Default
    private Date dateTime = new Date();

    @Column(nullable = false)
    private String event;

    @ManyToOne
    @JoinColumn(name = "order_no")
    private Order order;

    @Column
    private Integer moneyExchange; //TODO: Create Transactions table instead, can reference it here

    @Column(nullable = false)
    private Integer actualBalance; //TODO: remove this as well
}
