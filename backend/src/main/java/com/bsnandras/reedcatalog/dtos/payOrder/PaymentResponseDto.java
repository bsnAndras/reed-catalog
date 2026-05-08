package com.bsnandras.reedcatalog.dtos.payOrder;

import com.bsnandras.reedcatalog.models.Order;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

@Builder
public record PaymentResponseDto(
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        Instant transactionDateTime,

        String message,

        Order updatedOrder,

        @PositiveOrZero
        int moneyPaid
) {
}
