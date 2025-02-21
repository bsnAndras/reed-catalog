package com.bsnandras.reedcatalog.unitTests;

import com.bsnandras.reedcatalog.models.Customer;
import com.bsnandras.reedcatalog.repositories.CustomerRepository;
import com.bsnandras.reedcatalog.services.CustomerServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Captor
    private ArgumentCaptor<Customer> captor;

    public Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer(
                1L,
                "John Smith",
                10000,
                new HashSet<>()
        );
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
    }

    @Disabled
    @Test
    void getCustomerPageData() {
    }

    @Test()
    @DisplayName("Should set Customer Balance - HAPPY")
    void shouldSetBalance() {
        int initialBalance = customer.getBalance();
        int newBalance = initialBalance + (int) (Math.random() * 100) + 1;

        customerService.setBalance(customer.getId(), newBalance);

        verify(customerRepository).findById(customer.getId());
        verify(customerRepository).save(captor.capture());

        assertEquals(customer.getId(),captor.getValue().getId());
        assertEquals(newBalance, captor.getValue().getBalance());
        assertNotEquals(initialBalance, captor.getValue().getBalance());
    }

    @Disabled
    @Test
    void placeDebt() {
    }
}