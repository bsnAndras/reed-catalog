package com.bsnandras.reedcatalog.dtos.newOrder;

import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Builder
public record NewOrderRequestDto(
        Long partnerId,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        Date dateOfPurchase,
        int totalPrice,
        String notes
) {
}
