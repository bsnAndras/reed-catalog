package com.bsnandras.reedcatalog.unitTests;

import com.bsnandras.reedcatalog.dtos.PartnerPageResponseDto;
import com.bsnandras.reedcatalog.models.Order;
import com.bsnandras.reedcatalog.models.Partner;
import com.bsnandras.reedcatalog.repositories.OrderRepository;
import com.bsnandras.reedcatalog.repositories.PartnerRepository;
import com.bsnandras.reedcatalog.services.PartnerServiceImpl;
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
class PartnerServiceTest {
    @InjectMocks
    private PartnerServiceImpl partnerService;

    @Mock
    private PartnerRepository partnerRepository;

    @Mock
    private OrderRepository orderRepository;

    @Captor
    private ArgumentCaptor<Partner> captor;

    public Partner partner;

    @BeforeEach
    void setUp() {
        partner = new Partner(
                1L,
                "John Smith",
                -3000,
                new HashSet<>()
        );
        partner.getOrderList().addAll(
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
        partnerRepository.save(partner);
    }

    @Test
    @DisplayName("Should get Partner - HAPPY")
    void shouldGetPartner() {
        when(partnerRepository.findById(any())).thenReturn(Optional.of(partner));
        assertEquals(partner, partnerService.getPartner(1L));
        verify(partnerRepository).findById(1L);
    }

    @Test
    void shouldGetAllPartners() {
        //When
        partnerService.showAllPartners();
        //Then
        verify(partnerRepository).findAllByOrderByName();
    }

    @Test
    void getPartnerPageData() {
        //Given
        PartnerPageResponseDto dto = PartnerPageResponseDto.builder()
                .id(partner.getId())
                .name(partner.getName())
                .balance(partner.getBalance())
                .orderList(partner.getOrderList().stream().toList())
                .build();
        int totalBalanceOfAllOrders = 0;
        for (Order order : partner.getOrderList()) {
            totalBalanceOfAllOrders += order.getAmountToPay();
        }
        //Mocks
        when(partnerRepository.findById(any())).thenReturn(Optional.of(partner));
        when(orderRepository.findAllByPartner(partner))
                .thenReturn(partner
                        .getOrderList()
                        .stream()
                        .toList());
        //When
        PartnerPageResponseDto partnerPageDto = partnerService.getPartnerPageData(partner.getId());
        //Then
        verify(partnerRepository, times(2)).findById(partner.getId());
        verify(orderRepository).findAllByPartner(any());
        assertEquals(dto.id(), partnerPageDto.id());
        assertEquals(dto.name(), partnerPageDto.name());
        assertEquals(dto.orderList(), partnerPageDto.orderList());
        assertEquals(-totalBalanceOfAllOrders, partnerPageDto.balance());
        //TODO: should update the balance calculation later. The partner balance should be independent from the orders' balance.
        // The orders debt and the account balance should be summed together only on display, not in database.

    }

    @Test()
    @DisplayName("Should set Partner Balance - HAPPY")
    void shouldSetBalance() {
        //Given
        int initialBalance = partner.getBalance();
        int newBalance = initialBalance + (int) (Math.random() * 100) + 1;

        //When
        when(partnerRepository.findById(any())).thenReturn(Optional.of(partner));
        partnerService.setBalance(partner.getId(), newBalance);

        //Then
        verify(partnerRepository).findById(partner.getId());
        verify(partnerRepository, times(2)).save(captor.capture());

        assertEquals(partner.getId(), captor.getValue().getId());
        assertEquals(newBalance, captor.getValue().getBalance());
        assertNotEquals(initialBalance, captor.getValue().getBalance());
    }

    @Test
    @DisplayName("Should place 1000 debt to 0 balanced partner - HAPPY")
    void placeDebt() {
        //Set up
        partner.setBalance(0);
        Order newOrder = Order.builder()
                .id(1L)
                .totalPrice(1000)
                .amountToPay(1000)
                .build();

        partner.setOrderList(new HashSet<>(List.of(
                Order.builder()
                        .id(1L)
                        .totalPrice(3000)
                        .amountToPay(0)
                        .build(),
                newOrder
        )));
        partnerRepository.save(partner);

        //When
        when(partnerRepository.findById(any())).thenReturn(Optional.of(partner));
        partnerService.placeDebt(partner.getId(), newOrder);

        //Then
        verify(partnerRepository).findById(partner.getId());
        verify(partnerRepository, times(3)).save(captor.capture());

        assertEquals(partner.getId(), captor.getValue().getId());
        //TODO: later modify this to accomodate the separation of partner balance in DB from the Orders total debt
        assertEquals(-partner.getOrderList().stream().mapToInt(Order::getAmountToPay).sum(), captor.getValue().getBalance());
    }
}