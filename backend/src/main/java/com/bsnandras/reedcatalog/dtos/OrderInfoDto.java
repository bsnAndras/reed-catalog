package com.bsnandras.reedcatalog.dtos;

import java.util.Date;

public record OrderInfoDto(
        Long id,
        Date dateOfPurchase,
        int totalPrice,
        int amountToPay,
        String partnerName
) {
}
