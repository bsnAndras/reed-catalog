package com.bsnandras.reedcatalog.dtos.newOrder;

public record NewOrderRequestDto(
        Long customerId,
        int totalPrice
        ) {
}
