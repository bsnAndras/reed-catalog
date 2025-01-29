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

        newOrder = customerService.newOrder(requestDto.customerId(),newOrder);

        orderRepository.save(newOrder);
        //Todo: implement Reeds into this process later.
        logService.newLog(newOrder);

        return new NewOrderResponseDto("Order placed");
    }
}
