package com.bsnandras.reedcatalog.dtos;

import com.bsnandras.reedcatalog.dtos.links.PartnerLinkDTO;
import com.bsnandras.reedcatalog.models.Order;
import lombok.Builder;

import java.time.Instant;

@Builder
public record OrderInfoDto(
        Long id,
        Instant dateOfPurchase,
        int totalPrice,
        int amountToPay,
        String notes,
        PartnerLinkDTO partner
) {
    public static OrderInfoDto fromOrder(Order order) {
        if (order == null) {
            return null;
        }

        return OrderInfoDto.builder()
                .id(order.getId())
                .dateOfPurchase(order.getDateOfPurchase())
                .totalPrice(order.getTotalPrice())
                .amountToPay(order.getAmountToPay())
                .notes(order.getNotes())
                .partner(PartnerLinkDTO.fromPartner(order.getPartner()))
                .build();
    }
}
