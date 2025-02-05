package com.bsnandras.reedcatalog.dtos.paymentReceived;

import jakarta.validation.constraints.Positive;

public record PaymentRequestDto(
        @Positive
        Long orderId,
        @Positive
        int paymentAmount
) {

}
