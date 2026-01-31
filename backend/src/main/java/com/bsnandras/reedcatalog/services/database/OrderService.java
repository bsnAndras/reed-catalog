package com.bsnandras.reedcatalog.services.database;

import com.bsnandras.reedcatalog.errors.OrderNotFoundException;
import com.bsnandras.reedcatalog.models.Order;

public interface OrderService {
    Order saveOrder(Order order);

    Order getOrder(Long orderId);

    /**
     * Pays the order with the given amount of money. Returns the remaining debt on the order.
     *
     * @param order     the order to be paid
     * @param moneyPaid the money, to be paid
     * @return the remaining debt on the order, that is still to be paid. If the returned value is negative,
     * it means excess money was paid.
     */
    int payOrder(Order order, int moneyPaid) throws OrderNotFoundException;
}
