package com.bsnandras.reedcatalog.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Log {

    /**
     * The date and time of the log event (in UTC). Serves as the primary key.
     */
    @Id
    @Column(name = "date_time", nullable = false, unique = true, updatable = false)
    @Builder.Default
    private Instant dateTime = Instant.now();

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
