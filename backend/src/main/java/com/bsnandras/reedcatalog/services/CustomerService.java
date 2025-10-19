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

    /**
     * Method for placing debt on customer account
     * @param customerId customer's id
     * @param order the order to be paid, when money is available
     * @return the new account balance of the customer
     */
    int placeDebt(Long customerId, Order order);
}
