package com.bsnandras.reedcatalog.dtos.newOrder;

import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record NewOrderForm(
        Long partnerId,

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDateTime dateOfPurchase,

        String timezone,

        @PositiveOrZero
        int totalPrice,

        String notes
) {
}
