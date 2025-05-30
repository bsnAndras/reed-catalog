package com.bsnandras.reedcatalog.dtos.newOrder;

import com.bsnandras.reedcatalog.models.Order;

public record NewOrderResponseDto(
        String message,
        Order order
) {
}
