package com.bsnandras.reedcatalog.dtos.newOrder;

import com.bsnandras.reedcatalog.models.Order;

public record NewOrderResponseDto(
        String message,
        Order order
) {

    public static NewOrderResponseDto fromOrder(Order order) {
        return new NewOrderResponseDto(String.format("New order placed: %s", order.getNotes()), order);
    }
}
