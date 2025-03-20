package com.bsnandras.reedcatalog.services;

import com.bsnandras.reedcatalog.dtos.OrderInfoDto;
import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderRequestDto;
import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderResponseDto;
import com.bsnandras.reedcatalog.dtos.paymentReceived.PaymentRequestDto;
import com.bsnandras.reedcatalog.dtos.paymentReceived.PaymentResponseDto;
import com.bsnandras.reedcatalog.errors.OrderNotFoundException;

public interface OrderService {
    NewOrderResponseDto placeNewOrder(NewOrderRequestDto requestDto);

    PaymentResponseDto updateOrderWithPaymentReceived(PaymentRequestDto requestDto) throws OrderNotFoundException;

    OrderInfoDto getOrderByOrderId(Long orderId);
}
