package com.bsnandras.reedcatalog.services;

import com.bsnandras.reedcatalog.models.Customer;
import com.bsnandras.reedcatalog.models.Order;

import java.util.List;

public interface CustomerService {
    Customer getCustomer(Long id);

    List<Order> showOrderHistory(Long customerId);

    List<Customer> showAllCustomers();
}
