package com.bsnandras.reedcatalog.services.pages;

import com.bsnandras.reedcatalog.dtos.OrderInfoDto;
import com.bsnandras.reedcatalog.dtos.PartnerPageResponseDto;
import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderRequestDto;
import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderResponseDto;
import com.bsnandras.reedcatalog.dtos.paymentReceived.PaymentRequestDto;
import com.bsnandras.reedcatalog.dtos.paymentReceived.PaymentResponseDto;
import com.bsnandras.reedcatalog.models.Order;
import com.bsnandras.reedcatalog.models.Partner;
import com.bsnandras.reedcatalog.repositories.OrderRepository;
import com.bsnandras.reedcatalog.repositories.PartnerRepository;
import com.bsnandras.reedcatalog.services.database.LogService;
import com.bsnandras.reedcatalog.services.database.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerProfileServiceImpl implements PartnerProfileService {

    private final PartnerRepository partnerRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final LogService logService;

    @Override
    public Partner getPartner(Long id) {
        return partnerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Order> getOrderHistory(Long partnerId) {
        return orderRepository.findAllByPartner(getPartner(partnerId));
    }

    @Override
    public int setBalance(Long partnerId, int newBalance) {
        Partner partner = getPartner(partnerId);
        partner.setBalance(newBalance);
        partnerRepository.save(partner);
        return newBalance;
    }

    @Override
    public PartnerPageResponseDto getPartnerPageData(Long partnerId) {
        Partner partner = getPartner(partnerId);

        return PartnerPageResponseDto.builder()
                .id(partner.getId())
                .name(partner.getName())
                .balance(partner.getBalance())
                .orderList(getOrderHistory(partnerId))
                .build();
    }

    @Override
    public String getPartnerName(Long partnerId) {
        return getPartner(partnerId).getName();
    }

    @Override
    public NewOrderResponseDto placeNewOrder(NewOrderRequestDto requestDto) {
        Partner partner = getPartner(requestDto.partnerId());
        Order newOrder = orderService.placeNewOrder(partner, requestDto);

        NewOrderResponseDto responseDto = NewOrderResponseDto.fromOrder(newOrder);
        logService.newOrderLog(responseDto);

        return responseDto;
    }

    @Override
    public OrderInfoDto getOrderByOrderId(Long orderId) {
        Order order = orderService.getOrder(orderId);
        return OrderInfoDto.fromOrder(order);
    }

    @Override
    public PaymentResponseDto payOrder(PaymentRequestDto requestDto) {
        Order order = orderService.getOrder(requestDto.orderId());
        int remainingDebt = orderService.payOrder(requestDto);
        String responseMessage;

        if (remainingDebt > 0) {
            responseMessage = String.format("Payment received to order no.%d. Amount still to be payed: %d Ft",
                    order.getId(), order.getAmountToPay());
        } else {
            responseMessage = String.format("Payment received, order no.%d successfully payed.", order.getId());
        }
        if (remainingDebt < 0)
            responseMessage += String.format("\nExcess payment: %d Ft.", -remainingDebt);

        //TODO: handle excess payments properly
        PaymentResponseDto responseDto = PaymentResponseDto.builder()
                .updatedOrder(order)
                .message(responseMessage)
                .moneyPaid(requestDto.paymentAmount())
                .build();
        logService.newOrderLog(responseDto);

        return responseDto;
    }
}
