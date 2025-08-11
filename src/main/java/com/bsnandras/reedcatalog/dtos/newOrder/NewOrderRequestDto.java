package com.bsnandras.reedcatalog.dtos.newOrder;

public record NewOrderRequestDto(
        Long partnerId,
        int totalPrice
        ) {
}
