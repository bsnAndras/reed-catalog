package com.bsnandras.reedcatalog.dtos.paymentReceived;

import com.bsnandras.reedcatalog.models.Order;
import lombok.Builder;

@Builder
public record PaymentResponseDto(
        String message,
        PaymentRequestDto request,
        Order updatedOrder,
        int newPartnerBalance,
        int moneyPaid
) {

    public static PaymentResponseDto fromOrder(PaymentRequestDto requestDto, Order updatedOrder, int newPartnerBalance, int moneyPaid) {
        String message = "Payment of " + moneyPaid + " received for Order ID: " + updatedOrder.getId();
        return PaymentResponseDto.builder()
                .message(message)
                .request(requestDto)
                .updatedOrder(updatedOrder)
                .newPartnerBalance(newPartnerBalance)
                .moneyPaid(moneyPaid)
                .build();
    }
}
