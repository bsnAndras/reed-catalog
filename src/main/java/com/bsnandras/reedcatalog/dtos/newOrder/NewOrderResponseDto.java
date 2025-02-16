package com.bsnandras.reedcatalog.dtos.newOrder;

import lombok.Data;
import java.util.Date;

public record NewOrderResponseDto(
        String message,
        OrderDTO order
) {
    @Data
    public static class OrderDTO {
        Long id;
        Date dateOfPurchase;
        int totalPrice;
        Long customerId;
        String customerName;
        int amountToPay;

    }
}
