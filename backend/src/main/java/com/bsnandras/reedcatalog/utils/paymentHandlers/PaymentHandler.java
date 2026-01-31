package com.bsnandras.reedcatalog.utils.paymentHandlers;

import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderRequestDto;
import com.bsnandras.reedcatalog.dtos.paymentReceived.PaymentRequestDto;
import com.bsnandras.reedcatalog.models.Order;
import com.bsnandras.reedcatalog.models.Partner;

/**
 * Interface defining methods for handling payments and orders for partners.
 * Different implementations should provide various payment handling strategies.
 */
public interface PaymentHandler {

    /**
     * Method for adding money to the partner's balance from direct input (not from paying an order)
     *
     * @param amount the amount of money to be added to the partner's balance
     * @return the new balance after adding the money
     */
    int addMoney(Partner partner, int amount);

    /**
     * Method for withdrawing money from the partner's balance. This money will leave the system.
     *
     * @param amount the amount of money to be withdrawn from the partner's balance
     * @return the new balance after withdrawing the money
     */
    int withdrawMoney(Partner partner, int amount);

    int setBalance(Partner partner, int newBalance);

    /**
     * Method to be called when a new order is placed for the partner.
     *
     * @param partner    the partner placing the order
     * @param requestDto the new order request data
     * @return the created order
     */
    Order placeNewOrder(Partner partner, NewOrderRequestDto requestDto);

    /**
     * Method to be called when a payment is made towards an existing order.
     *
     * @param requestDto the payment request data, contains order ID and payment information (amount, date, notes)
     * @return the updated order after processing the payment
     */
    Order payOrder(PaymentRequestDto requestDto);
}
