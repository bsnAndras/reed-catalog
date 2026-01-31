package com.bsnandras.reedcatalog.services.database;

import com.bsnandras.reedcatalog.errors.OrderNotFoundException;
import com.bsnandras.reedcatalog.models.Order;
import com.bsnandras.reedcatalog.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    /**
     * Places a new order without upfront payment, and saves it to the database.
     *
     * @param newOrder the new order to be saved
     * @return the order that was saved
     */
    @Override
    public Order saveOrder(Order newOrder) {
        orderRepository.save(newOrder);

        return newOrder;
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("No order found under this id: " + orderId));
    }

    @Override
    public int payOrder(Order order, int moneyPaid) {
        int debt = order.getAmountToPay();
        int remainingDebt = debt - moneyPaid; //can go negative if excess money is paid

        order.setAmountToPay(Math.max(remainingDebt, 0));

        saveOrder(order);

        return remainingDebt;
    }
}
