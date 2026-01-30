package com.bsnandras.reedcatalog.services.pages;

import com.bsnandras.reedcatalog.dtos.OrderInfoDto;
import com.bsnandras.reedcatalog.dtos.PartnerPageResponseDto;
import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderRequestDto;
import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderResponseDto;
import com.bsnandras.reedcatalog.dtos.paymentReceived.PaymentRequestDto;
import com.bsnandras.reedcatalog.dtos.paymentReceived.PaymentResponseDto;
import com.bsnandras.reedcatalog.models.Order;
import com.bsnandras.reedcatalog.models.Partner;
import jakarta.validation.Valid;

import java.util.List;

public interface PartnerProfileService {

    Partner getPartner(Long id);

    int setBalance(Long partnerId, int newBalance);

    List<Order> getOrderHistory(Long partnerId);

    PartnerPageResponseDto getPartnerPageData(Long partnerId);

    NewOrderResponseDto placeNewOrder(NewOrderRequestDto requestDto);

    OrderInfoDto getOrderByOrderId(Long orderId);

    PaymentResponseDto payOrder(@Valid PaymentRequestDto requestDto);
}
