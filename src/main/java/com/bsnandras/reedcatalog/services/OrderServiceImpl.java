package com.bsnandras.reedcatalog.services;

import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderRequestDto;
import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderResponseDto;
import com.bsnandras.reedcatalog.models.Customer;
import com.bsnandras.reedcatalog.models.Order;
import com.bsnandras.reedcatalog.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final LogService logService;

    /**
     * Place a new order without upfront payment (TODO: implement order payment next),
     * and updates the customer's account balance and the order's amountToPay accordingly.
     *
     * @param requestDto the new order request
     * @return a message whether the order registration was successful
     */
    @Override
    public NewOrderResponseDto placeNewOrder(NewOrderRequestDto requestDto) {
        Customer customer = customerService.getCustomer(requestDto.customerId());

        // Place new order (Reeds will be empty Reed objects for now.)
        Order newOrder = Order.builder()
                .dateOfPurchase(new Date())
                .customer(customer)
                .totalPrice(requestDto.totalPrice())
                .amountToPay(requestDto.totalPrice())
                .build();

        //Todo: implement Reeds into this process later.

        newOrder = payOrderFromCustomerBalance(newOrder);
        orderRepository.save(newOrder);
        customerService.placeDebt(customer.getId(),newOrder);

        logService.newOrderLog(newOrder); // TODO: place this line to controller

        return new NewOrderResponseDto("Order placed");
    }

    /**
     * The method for paying an order from customer's excess money from his/her account
     *
     * @param order The Order you wish to pay from Customer balance
     * @return the Order after paying from the customer balance
     */
    public Order payOrderFromCustomerBalance(Order order) {
        Customer customer = order.getCustomer();
        int currentBalance = customer.getBalance();
        int customerDebt = order.getAmountToPay();

        int amountToBePayed;

        if ((currentBalance <= 0 && customerDebt >= 0) || customerDebt == 0) {
            return order;
        }
        if (customerDebt < 0) {
            amountToBePayed = customerDebt;
        } else {
            amountToBePayed = Math.min(currentBalance, customerDebt);
        }

        customerService.setBalance(customer.getId(),
                currentBalance - amountToBePayed);

        order.setAmountToPay(customerDebt - amountToBePayed);

        return order;
    }
}
