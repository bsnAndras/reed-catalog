package com.bsnandras.reedcatalog.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Represents a financial transaction between two partners.
 */
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class Transaction {

    /**
     * The timestamp of the transaction (in UTC).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Builder.Default
    Instant timestamp = Instant.now();

    @NotNull
    int amount;

    /**
     * A brief description of the transaction (optional). The maximum length is 50 characters.
     */
    @Max(value = 50, message = "Description cannot be longer than 50 characters")
    String description;

    @ManyToOne
    @JoinColumn(name = "from_partner", nullable = false)
    Partner fromPartner;

    @ManyToOne
    @JoinColumn(name = "to_partner", nullable = false)
    Partner toPartner;
}
