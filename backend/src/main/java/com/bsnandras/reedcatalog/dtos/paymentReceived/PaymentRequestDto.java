package com.bsnandras.reedcatalog.dtos.paymentReceived;

import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public record PaymentRequestDto(
        @Positive
        Long orderId,

        @Positive
        int paymentAmount,

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        Date transactionDate,

        String note
) {

}
