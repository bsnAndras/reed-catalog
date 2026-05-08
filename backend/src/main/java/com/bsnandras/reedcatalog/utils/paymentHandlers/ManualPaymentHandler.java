package com.bsnandras.reedcatalog.utils.paymentHandlers;

import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderRequestDto;
import com.bsnandras.reedcatalog.dtos.payOrder.PaymentRequestDto;
import com.bsnandras.reedcatalog.models.Order;
import com.bsnandras.reedcatalog.models.Partner;
import com.bsnandras.reedcatalog.repositories.PartnerRepository;
import com.bsnandras.reedcatalog.services.database.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Payment handler for manual payments.
 * No automatic processing (balance update or debt payment) is done upon any method calls.
 */
@RequiredArgsConstructor
@Component
public class ManualPaymentHandler implements PaymentHandler {

    private final OrderService orderService;
    private final PartnerRepository partnerRepository;

    @Override
    public int addMoney(Partner partner, int amount) {
        int newBalance = partner.getBalance() + amount;
        partner.setBalance(newBalance);

        return newBalance;
    }

    @Override
    public int withdrawMoney(Partner partner, int amount) {
        int newBalance = partner.getBalance() - amount;
        partner.setBalance(newBalance);

        return newBalance;
    }

    @Override
    public int setBalance(Partner partner, int newBalance) {
        partner.setBalance(newBalance);
        partnerRepository.save(partner);

        return newBalance;
    }

    /**
     * Method to be called when a new order is placed for the partner.
     * It creates a new order with the provided details and saves it.
     * No other automatic actions are taken.
     *
     * @param partner    the partner placing the order
     * @param requestDto the new order request data
     * @return the created order
     */
    @Override
    public Order placeNewOrder(Partner partner, NewOrderRequestDto requestDto) {
        Order newOrder = Order.builder()
                .dateOfPurchase(requestDto.dateOfPurchase())
                .partner(partner)
                .totalPrice(requestDto.totalPrice())
                .amountToPay(requestDto.totalPrice())
                .notes(requestDto.notes())
                .build();

        return orderService.saveOrder(newOrder);
    }

    /**
     * Method to be called when a payment is made towards an existing order.
     * It automatically updates the partner's balance with excess money,
     * but does nothing else.
     * Multiple payments must be handled one-by-one manually.
     *
     * @param requestDto contains order ID and payment information (amount, date, notes)
     * @return the updated order after processing the payment
     */
    @Override
    public Order payOrder(PaymentRequestDto requestDto) {
        Order order = orderService.getOrder(requestDto.orderId());
        Partner partner = order.getPartner();
        int moneyPaid = requestDto.paymentAmount();

        int excessMoney = Math.max(0, -orderService.payOrder(order, moneyPaid));
        setBalance(partner, partner.getBalance() + excessMoney);

        return order;
    }
}
