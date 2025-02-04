package com.bsnandras.reedcatalog.services;

import com.bsnandras.reedcatalog.dtos.CustomerPageResponseDto;
import com.bsnandras.reedcatalog.models.Customer;
import com.bsnandras.reedcatalog.models.Order;

import java.util.List;

public interface CustomerService {
    Customer getCustomer(Long id);

    List<Order> getOrderHistory(Long customerId);

    List<Customer> showAllCustomers();

    CustomerPageResponseDto getCustomerPageData(Long customerId);

    int setBalance(Long customerId, int newBalance);
}
