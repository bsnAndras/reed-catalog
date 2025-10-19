package com.bsnandras.reedcatalog.unitTests;

import com.bsnandras.reedcatalog.dtos.CustomerPageResponseDto;
import com.bsnandras.reedcatalog.models.Customer;
import com.bsnandras.reedcatalog.models.Order;
import com.bsnandras.reedcatalog.repositories.CustomerRepository;
import com.bsnandras.reedcatalog.repositories.OrderRepository;
import com.bsnandras.reedcatalog.services.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
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
        customerRepository.save(customer);
    }

    @Test
    @DisplayName("Should get Customer - HAPPY")
    void shouldGetCustomer() {
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
        assertEquals(customer, customerService.getCustomer(1L));
        verify(customerRepository).findById(1L);
    }

    @Test
    void shouldGetAllCustomers() {
        //When
        customerService.showAllCustomers();
        //Then
        verify(customerRepository).findAllByOrderByName();
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
        //Mocks
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
        when(orderRepository.findAllByCustomer(customer))
                .thenReturn(customer
                        .getOrderList()
                        .stream()
                        .toList());
        //When
        CustomerPageResponseDto customerPageDto = customerService.getCustomerPageData(customer.getId());
        //Then
        verify(customerRepository, times(2)).findById(customer.getId());
        verify(orderRepository).findAllByCustomer(any());
        assertEquals(dto.id(), customerPageDto.id());
        assertEquals(dto.name(), customerPageDto.name());
        assertEquals(dto.orderList(), customerPageDto.orderList());
        assertEquals(-totalBalanceOfAllOrders, customerPageDto.balance());
        //TODO: should update the balance calculation later. The customer balance should be independent from the orders' balance.
        // The orders debt and the account balance should be summed together only on display, not in database.

    }

    @Test()
    @DisplayName("Should set Customer Balance - HAPPY")
    void shouldSetBalance() {
        //Given
        int initialBalance = customer.getBalance();
        int newBalance = initialBalance + (int) (Math.random() * 100) + 1;

        //When
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
        customerService.setBalance(customer.getId(), newBalance);

        //Then
        verify(customerRepository).findById(customer.getId());
        verify(customerRepository, times(2)).save(captor.capture());

        assertEquals(customer.getId(), captor.getValue().getId());
        assertEquals(newBalance, captor.getValue().getBalance());
        assertNotEquals(initialBalance, captor.getValue().getBalance());
    }

    @Test
    @DisplayName("Should place 1000 debt to 0 balanced customer - HAPPY")
    void placeDebt() {
        //Set up
        customer.setBalance(0);
        Order newOrder = Order.builder()
                .id(1L)
                .totalPrice(1000)
                .amountToPay(1000)
                .build();

        customer.setOrderList(new HashSet<>(List.of(
                Order.builder()
                        .id(1L)
                        .totalPrice(3000)
                        .amountToPay(0)
                        .build(),
                newOrder
        )));
        customerRepository.save(customer);

        //When
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
        customerService.placeDebt(customer.getId(), newOrder);

        //Then
        verify(customerRepository).findById(customer.getId());
        verify(customerRepository, times(3)).save(captor.capture());

        assertEquals(customer.getId(), captor.getValue().getId());
        //TODO: later modify this to accomodate the separation of customer balance in DB from the Orders total debt
        assertEquals(-customer.getOrderList().stream().mapToInt(Order::getAmountToPay).sum(), captor.getValue().getBalance());
    }
}