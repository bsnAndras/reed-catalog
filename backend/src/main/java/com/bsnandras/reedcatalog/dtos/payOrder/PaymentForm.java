package com.bsnandras.reedcatalog.dtos.payOrder;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record PaymentForm(
        @Positive
        Long orderId,

        @PositiveOrZero
        int paymentAmount,

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDateTime transactionDateTime,

        String timezone
) {

}
