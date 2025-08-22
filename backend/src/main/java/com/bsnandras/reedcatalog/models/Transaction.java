package com.bsnandras.reedcatalog.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Builder.Default
    LocalDateTime timestamp = LocalDateTime.now();

    @NotNull
    int amount;

    @Max(value = 50, message = "Description cannot be longer than 50 characters")
    String description;

    @ManyToOne
    @JoinColumn(name = "from_partner", nullable = false)
    Partner fromPartner;

    @ManyToOne
    @JoinColumn(name = "to_partner", nullable = false)
    Partner toPartner;
}
