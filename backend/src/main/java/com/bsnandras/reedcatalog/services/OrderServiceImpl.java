package com.bsnandras.reedcatalog.services;

import com.bsnandras.reedcatalog.dtos.OrderInfoDto;
import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderRequestDto;
import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderResponseDto;
import com.bsnandras.reedcatalog.dtos.paymentReceived.PaymentRequestDto;
import com.bsnandras.reedcatalog.dtos.paymentReceived.PaymentResponseDto;
import com.bsnandras.reedcatalog.errors.OrderNotFoundException;
import com.bsnandras.reedcatalog.models.Order;
import com.bsnandras.reedcatalog.models.Partner;
import com.bsnandras.reedcatalog.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final PartnerService partnerService;

    /**
     * Place a new order without upfront payment,
     * and updates the partner's account balance and the order's amountToPay accordingly.
     *
     * @param requestDto the new order request
     * @return a message whether the order registration was successful
     */
    @Override
    public NewOrderResponseDto placeNewOrder(NewOrderRequestDto requestDto) {
        Partner partner = partnerService.getPartner(requestDto.partnerId());

        // Place new order (Reeds will not be added for now.)
        //Todo: implement Reeds into this process later.
        Order newOrder = Order.builder()
                .dateOfPurchase(new Date())
                .partner(partner)
                .totalPrice(requestDto.totalPrice())
                .amountToPay(requestDto.totalPrice())
                .build();

        newOrder = payOrderFromPartnerBalance(newOrder);
        orderRepository.save(newOrder);
        partnerService.placeDebt(partner.getId(), newOrder);

        return new NewOrderResponseDto("Order placed", newOrder);
    }

    @Override
    public OrderInfoDto getOrderByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new OrderNotFoundException("No order found under this id: " + orderId));
        String partnerName = order.getPartner().getName();

        return new OrderInfoDto(
                orderId,
                order.getDateOfPurchase(),
                order.getTotalPrice(),
                order.getAmountToPay(),
                partnerName
        );
    }

    /**
     * The method for paying an order from partner's excess money from his/her account
     *
     * @param order The Order you wish to pay from Partner balance
     * @return the Order after paying from the partner balance
     */
    public Order payOrderFromPartnerBalance(Order order) {
        Partner partner = order.getPartner();
        int currentBalance = partner.getBalance();
        int partnerDebt = order.getAmountToPay();

        int amountToBePayed;

        if ((currentBalance <= 0 && partnerDebt >= 0) || partnerDebt == 0) {
            return order;
        }
        if (partnerDebt < 0) {
            amountToBePayed = partnerDebt;
        } else {
            amountToBePayed = Math.min(currentBalance, partnerDebt);
        }

        partnerService.setBalance(partner.getId(),
                currentBalance - amountToBePayed);

        order.setAmountToPay(partnerDebt - amountToBePayed);

        return order;
    }

    /**
     * Pays the order with the money the user paid. If the user paid too much, returns the excess amount.
     *
     * @param order     the order to be paid
     * @param moneyPaid the money, the partner paid
     * @return the excess money, that was not used up for paying debt
     */
    public int payOrderFromNewMoney(Order order, int moneyPaid) {
        int partnerDebt = order.getAmountToPay();
        int amountToBePayed = Math.min(partnerDebt, moneyPaid);
        Partner partner = order.getPartner();
        order.setAmountToPay(partnerDebt-amountToBePayed);
        partnerService.setBalance(partner.getId(), partner.getBalance() + amountToBePayed);
        return moneyPaid - amountToBePayed;
    }

    @Override
    public PaymentResponseDto updateOrderWithPaymentReceived(PaymentRequestDto requestDto) {
        //TODO: have some bug to fix: when paying an order with more money than needed, the balance does not update as it should
        String responseMessage;
        Order order = orderRepository.findById(requestDto.orderId())
                .orElseThrow(() -> new OrderNotFoundException("Order cannot be found under this ID"));

        order = payOrderFromPartnerBalance(order);
        Partner partner = order.getPartner();

        int excessFromPayment = payOrderFromNewMoney(order, requestDto.paymentAmount());
        int newPartnerBalance = partner.getBalance();

        orderRepository.save(order);

        if (order.getAmountToPay() > 0) {
            responseMessage = String.format("Payment received to order no.%d. Amount still to be payed: %d Ft",
                    order.getId(), order.getAmountToPay());
        } else {
            responseMessage = String.format("Payment received, order no.%d successfully payed.", order.getId());
        }
        if (excessFromPayment > 0)
            responseMessage += String.format("\nExcess payment: %d Ft.", excessFromPayment);
        //Todo: handle the excess money somehow


        return PaymentResponseDto.builder()
                .request(requestDto)
                .newPartnerBalance(newPartnerBalance)
                .updatedOrder(order)
                .moneyPaid(requestDto.paymentAmount() - excessFromPayment)
                .message(responseMessage)
                .build();
    }
}
