package com.bsnandras.reedcatalog.services;

import com.bsnandras.reedcatalog.models.Customer;
import com.bsnandras.reedcatalog.models.Order;
import com.bsnandras.reedcatalog.repositories.CustomerRepository;
import com.bsnandras.reedcatalog.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Order> showOrderHistory(Long customerId) {
        return orderRepository.findAllByCustomer(getCustomer(customerId));
    }

    @Override
    public List<Customer> showAllCustomers() {
        return customerRepository.findAll();
    }
}
