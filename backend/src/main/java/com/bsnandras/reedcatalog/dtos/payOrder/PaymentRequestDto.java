package com.bsnandras.reedcatalog.dtos.payOrder;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

@Builder
public record PaymentRequestDto(
        @Positive
        Long orderId,

        @PositiveOrZero
        int paymentAmount,

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        Instant transactionDateTime
) {

}
