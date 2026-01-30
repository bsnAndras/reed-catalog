package com.bsnandras.reedcatalog.dtos.paymentReceived;

import com.bsnandras.reedcatalog.models.Order;
import lombok.Builder;

@Builder
public record PaymentResponseDto(
        //TODO: should include payment time
        String message,
        Order updatedOrder,
        int moneyPaid
) {
}
