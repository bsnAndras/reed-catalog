package com.bsnandras.reedcatalog.services.pages;

import com.bsnandras.reedcatalog.dtos.OrderInfoDto;
import com.bsnandras.reedcatalog.dtos.PartnerPageResponseDto;
import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderRequestDto;
import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderResponseDto;
import com.bsnandras.reedcatalog.dtos.payOrder.PaymentRequestDto;
import com.bsnandras.reedcatalog.dtos.payOrder.PaymentResponseDto;
import com.bsnandras.reedcatalog.models.Order;
import com.bsnandras.reedcatalog.models.Partner;
import com.bsnandras.reedcatalog.repositories.OrderRepository;
import com.bsnandras.reedcatalog.repositories.PartnerRepository;
import com.bsnandras.reedcatalog.services.database.LogService;
import com.bsnandras.reedcatalog.services.database.OrderService;
import com.bsnandras.reedcatalog.utils.ServiceFactory;
import com.bsnandras.reedcatalog.utils.paymentHandlers.PaymentHandler;
import com.bsnandras.reedcatalog.utils.paymentHandlers.PaymentHandlerStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PartnerProfileServiceImpl implements PartnerProfileService {

    private final PartnerRepository partnerRepository;
    private final OrderRepository orderRepository;

    private final OrderService orderService;
    private final LogService logService;

    private final ServiceFactory serviceFactory;

    @Override
    public Partner getPartner(Long id) {
        return partnerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Could not find partner with id: " + id));
    }

    @Override
    public List<Order> getOrderHistory(Long partnerId) {
        return orderRepository.findAllByPartner(getPartner(partnerId));
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
    public NewOrderResponseDto placeNewOrder(NewOrderRequestDto requestDto) {
        PaymentHandler handler = serviceFactory.getPaymentHandler(PaymentHandlerStrategy.MANUAL);
        Partner partner = getPartner(requestDto.partnerId());
        Order newOrder = handler.placeNewOrder(partner, requestDto);

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
        PaymentHandler handler = serviceFactory.getPaymentHandler(PaymentHandlerStrategy.MANUAL);
        Order order = handler.payOrder(requestDto);

        int remainingDebt = order.getAmountToPay();

        String responseMessage; //TODO: message gen. should be placed in logService

        if (remainingDebt > 0) {
            responseMessage = String.format("Payment received to order no.%d. Amount still to be payed: %d Ft",
                    order.getId(), order.getAmountToPay());
        } else {
            responseMessage = String.format("Payment received, order no.%d successfully payed.", order.getId());
        }
        if (remainingDebt < 0)
            //TODO: handle excess payment for response, this branch is currently unavailable.
            // Currently, excess payment is added to partner balance, but not mentioned in response.
            responseMessage += String.format("\nExcess payment: %d Ft.", -remainingDebt);

        PaymentResponseDto responseDto = PaymentResponseDto.builder()
                .transactionDateTime(requestDto.transactionDateTime())
                .updatedOrder(order)
                .message(responseMessage)
                .moneyPaid(requestDto.paymentAmount())
                .build();

        logService.newOrderLog(responseDto);

        return responseDto;
    }
}
