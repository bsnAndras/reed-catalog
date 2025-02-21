package com.bsnandras.reedcatalog.unitTests;

import com.bsnandras.reedcatalog.dtos.CustomerPageResponseDto;
import com.bsnandras.reedcatalog.models.Customer;
import com.bsnandras.reedcatalog.models.Order;
import com.bsnandras.reedcatalog.repositories.CustomerRepository;
import com.bsnandras.reedcatalog.repositories.OrderRepository;
import com.bsnandras.reedcatalog.services.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OrderRepository orderRepository;

    @Captor
    private ArgumentCaptor<Customer> captor;

    public Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer(
                1L,
                "John Smith",
                -3000,
                new HashSet<>()
        );
        customer.getOrderList().addAll(
                List.of(
                        Order.builder()
                                .id(1L)
                                .totalPrice(10000)
                                .amountToPay(0)
                                .build(),
                        Order.builder()
                                .id(2L)
                                .totalPrice(20000)
                                .amountToPay(1000)
                                .build(),
                        Order.builder()
                                .id(3L)
                                .totalPrice(30000)
                                .amountToPay(2000)
                                .build()
                ));
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
        when(orderRepository.findAllByCustomer(customer))
                .thenReturn(customer
                        .getOrderList()
                        .stream()
                        .toList());
    }

    @Test
    void getCustomerPageData() {
        //Given
        CustomerPageResponseDto dto = CustomerPageResponseDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .balance(customer.getBalance())
                .orderList(customer.getOrderList().stream().toList())
                .build();
        int totalBalanceOfAllOrders = 0;
        for (Order order : customer.getOrderList()) {
            totalBalanceOfAllOrders += order.getAmountToPay();
        }
        //When
        CustomerPageResponseDto customerPageDto = customerService.getCustomerPageData(customer.getId());
        //Then
        verify(customerRepository,times(2)).findById(customer.getId());
        assertEquals(dto.id(),customerPageDto.id());
        assertEquals(dto.name(), customerPageDto.name());
        assertEquals(-totalBalanceOfAllOrders, customerPageDto.balance());
        //TODO: should update the balance calculation later. The customer balance should be independent from the orders' balance.
        // The orders debt and the account balance should be summed together only on display, not in database.

        assertEquals(dto.orderList(), customerPageDto.orderList());
    }

    @Test()
    @DisplayName("Should set Customer Balance - HAPPY")
    void shouldSetBalance() {
        //Given
        int initialBalance = customer.getBalance();
        int newBalance = initialBalance + (int) (Math.random() * 100) + 1;

        //When
        customerService.setBalance(customer.getId(), newBalance);

        //Then
        verify(customerRepository).findById(customer.getId());
        verify(customerRepository).save(captor.capture());

        assertEquals(customer.getId(), captor.getValue().getId());
        assertEquals(newBalance, captor.getValue().getBalance());
        assertNotEquals(initialBalance, captor.getValue().getBalance());
    }

    @Disabled
    @Test
    void placeDebt() {
    }
}