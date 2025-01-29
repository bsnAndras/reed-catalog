package com.bsnandras.reedcatalog.services;

import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderRequestDto;
import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderResponseDto;

public interface OrderService {
    NewOrderResponseDto placeNewOrder(NewOrderRequestDto requestDto);
}
