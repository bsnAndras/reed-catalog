package com.bsnandras.reedcatalog.dtos.paymentReceived;

import com.bsnandras.reedcatalog.models.Order;
import lombok.Builder;

@Builder
public record PaymentResponseDto(
        String message,
        PaymentRequestDto request,
        Order updatedOrder,
        int newCustomerBalance,
        int moneyPaid
) {
}
