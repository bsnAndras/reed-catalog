package com.bsnandras.reedcatalog.dtos.newOrder;

import lombok.Builder;

@Builder
public record NewOrderRequestDto(
        Long partnerId,
        int totalPrice,
        String notes
) {
}
