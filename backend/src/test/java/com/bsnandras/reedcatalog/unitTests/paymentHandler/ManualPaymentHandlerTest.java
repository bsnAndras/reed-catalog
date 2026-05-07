package com.bsnandras.reedcatalog.unitTests.paymentHandler;

import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderRequestDto;
import com.bsnandras.reedcatalog.models.Order;
import com.bsnandras.reedcatalog.models.Partner;
import com.bsnandras.reedcatalog.repositories.PartnerRepository;
import com.bsnandras.reedcatalog.services.database.OrderService;
import com.bsnandras.reedcatalog.utils.paymentHandlers.ManualPaymentHandler;
import com.bsnandras.reedcatalog.utils.paymentHandlers.PaymentHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ManualPaymentHandlerTest {

    private PaymentHandler handler;

    @Mock
    private OrderService orderService;

    @Mock
    private PartnerRepository partnerRepository;

    private Partner partner;
    private List<Order> orders;

    @BeforeEach
    void setup() {
        handler = new ManualPaymentHandler(orderService, partnerRepository);
        partner = Partner.builder()
                .id(1L)
                .name("Test Partner")
                .balance(1000)
                .orderList(new ArrayList<>())
                .build();

        orders = partner.getOrderList();
        orders.add(Order.builder()
                .id(1L)
                .partner(partner)
                .totalPrice(1000)
                .amountToPay(0)
                .build());
    }

    @Test
    @Disabled
    @DisplayName("Add money without affecting debt")
    public void testAddMoney() {
        // Arrange
        Partner partnerSpy = spy(partner);
        int origBalance = partner.getBalance();
        int amountToAdd = 500;

        // Act
        int newBalance = handler.addMoney(partnerSpy, amountToAdd);

        // Assert
        verify(partnerSpy).setBalance(anyInt());
        verify(partnerRepository).save(partnerSpy);
        verify(orderService, never()).saveOrder(any(Order.class));

        assertEquals(origBalance + amountToAdd, newBalance);
    }

    @Test
    @Disabled
    @DisplayName("Withdraw money without affecting debt")
    void withdrawMoneyDebtUnchanged() {
        // Arrange
        int origBalance = partner.getBalance();
        int amountToWithdraw = 500;
        Partner partnerSpy = spy(partner);

        // Act
        int newBalance = handler.withdrawMoney(partnerSpy, amountToWithdraw);

        // Assert
        verify(partnerSpy).setBalance(anyInt());
        verify(partnerRepository).save(partnerSpy);
        verify(orderService, never()).saveOrder(any(Order.class));

        assertEquals(origBalance - amountToWithdraw, newBalance);
    }

    @Test
    void setBalance() {
        //Arrange
        int newBalance = 2000;
        Partner partnerSpy = spy(partner);

        //Act
        int returnedBalance = handler.setBalance(partnerSpy, newBalance);

        //Assert
        verify(partnerSpy).setBalance(newBalance);
        verify(partnerRepository).save(partnerSpy);

        assertEquals(newBalance, returnedBalance);
    }

    @Test
    @Disabled
    @DisplayName("place new order without affecting balance")
    void placeNewOrder() {
        //Arrange
        int origBalance = partner.getBalance();

        List<Order> origOrderList = new ArrayList<>(orders);
        Order newOrder = Order.builder()
                .id(100L)
                .partner(partner)
                .totalPrice(1500)
                .amountToPay(1500)
                .notes("Test order")
                .build();
        NewOrderRequestDto dto = new NewOrderRequestDto(partner.getId(), newOrder.getTotalPrice(), newOrder.getNotes());

        orders.add(newOrder);
        List<Order> expectedOrders = new ArrayList<>(orders);

        //Mock
        Partner partnerSpy = spy(partner);
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.captor();
        Order capturedOrder;

        //Act
        handler.placeNewOrder(partnerSpy, dto);

        //Assert
        verify(partnerSpy, never()).setBalance(anyInt());
        verify(orderService).saveOrder(orderCaptor.capture());
        capturedOrder = orderCaptor.getValue();
        capturedOrder.setId(newOrder.getId());
        verify(partnerRepository).save(partnerSpy);

        assertEquals(newOrder, capturedOrder);
        assertEquals(expectedOrders, partner.getOrderList());
        assertEquals(origBalance, partner.getBalance());
    }

    @Test
    @DisplayName("pay single order without affecting balance")
    @Disabled
    void payOrder() {
        //Arrange
        //Act
        //Assert
    }
}
