package com.bsnandras.reedcatalog.dtos;

import com.bsnandras.reedcatalog.models.Order;
import lombok.Builder;

import java.util.List;
@Builder
public record PartnerPageResponseDto(
        Long id,
        String name,
        int balance,
        List<Order> orderList
) {
}
