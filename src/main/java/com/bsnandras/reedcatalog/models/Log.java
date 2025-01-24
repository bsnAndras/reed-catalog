package com.bsnandras.reedcatalog.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    @Id
    @Column(name = "date_time", nullable = false, unique = true, updatable = false)
    private Date dateTime = new Date();

    @Column(nullable = false)
    private String event;

    @OneToOne
    @JoinColumn(name = "order_no", referencedColumnName = "id")
    private Order order;

    private int moneyExchange;

    @Column(nullable = false)
    private int actualBalance;
}
