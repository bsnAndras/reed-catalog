package com.bsnandras.reedcatalog.services;

import com.bsnandras.reedcatalog.dtos.CustomerPageResponseDto;
import com.bsnandras.reedcatalog.models.Customer;
import com.bsnandras.reedcatalog.models.Order;
import com.bsnandras.reedcatalog.repositories.CustomerRepository;
import com.bsnandras.reedcatalog.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Order> getOrderHistory(Long customerId) {
        return orderRepository.findAllByCustomer(getCustomer(customerId));
    }

    @Override
    public List<Customer> showAllCustomers() {
        return customerRepository.findAllByOrderByName();
    }

    @Override
    public CustomerPageResponseDto getCustomerPageData(Long customerId) {
        Customer customer = getCustomer(customerId);
        return CustomerPageResponseDto.builder()
                .id(customerId)
                .name(customer.getName())
                .balance(customer.getBalance())
                .orderList(getOrderHistory(customerId))
                .build();
    }

    @Override
    public int setBalance(Long customerId, int newBalance) {
        Customer customer = getCustomer(customerId);
        customer.setBalance(newBalance);
        customerRepository.save(customer);
        return newBalance;
    }

    @Override
    public int placeDebt(Long customerId, Order order) {
        Customer customer = getCustomer(customerId);
        int amountToPay = order.getAmountToPay();
        customer.setBalance(customer.getBalance() - amountToPay);
        customerRepository.save(customer);
        return customer.getBalance();
    }
}
