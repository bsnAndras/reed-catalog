package com.bsnandras.reedcatalog.services.database;

import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderRequestDto;
import com.bsnandras.reedcatalog.dtos.paymentReceived.PaymentRequestDto;
import com.bsnandras.reedcatalog.errors.OrderNotFoundException;
import com.bsnandras.reedcatalog.models.Order;
import com.bsnandras.reedcatalog.models.Partner;

public interface OrderService {
    Order placeNewOrder(Partner partner, NewOrderRequestDto requestDto);

    Order getOrder(Long orderId);

    /**
     * This method updates the order with the received payment information and returns the remaining amount to pay.
     *
     * @return the remaining amount to pay after the payment is received
     */
    int payOrder(PaymentRequestDto requestDto) throws OrderNotFoundException;
}
