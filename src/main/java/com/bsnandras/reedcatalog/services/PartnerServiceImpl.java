package com.bsnandras.reedcatalog.services;

import com.bsnandras.reedcatalog.dtos.PartnerPageResponseDto;
import com.bsnandras.reedcatalog.models.Order;
import com.bsnandras.reedcatalog.models.Partner;
import com.bsnandras.reedcatalog.repositories.OrderRepository;
import com.bsnandras.reedcatalog.repositories.PartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.rmi.AlreadyBoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;
    private final OrderRepository orderRepository;

    @Override
    public Partner getPartner(Long id) {
        return partnerRepository.findById(id).orElse(null);
    }

    @Override
    public boolean addPartner(Partner partner) throws IllegalArgumentException {
        if (partner.getName().isBlank()) {
            throw new IllegalArgumentException("Invalid partner name upon adding new Partner. Empty name is not allowed.");
        }
        if (partner.getBalance() < 0) {
            throw new IllegalArgumentException("Invalid balance value upon adding new Partner. Balance should be non-negative.");
        }
        if (partnerRepository.existsByName(partner.getName())) {
            throw new IllegalArgumentException(new AlreadyBoundException("Partner with this name already exists"));
        }

        partnerRepository.save(partner);
        return true;
    }

    @Override
    public List<Order> getOrderHistory(Long partnerId) {
        return orderRepository.findAllByPartner(getPartner(partnerId));
    }

    @Override
    public List<Partner> showAllPartners() {
        return partnerRepository.findAllByOrderByName();
    }

    @Override
    public PartnerPageResponseDto getPartnerPageData(Long partnerId) {
        Partner partner = getPartner(partnerId);
        return PartnerPageResponseDto.builder()
                .id(partnerId)
                .name(partner.getName())
                .balance(partner.getBalance())
                .orderList(getOrderHistory(partnerId))
                .build();
    }

    @Override
    public int setBalance(Long partnerId, int newBalance) {
        Partner partner = getPartner(partnerId);
        partner.setBalance(newBalance);
        partnerRepository.save(partner);
        return newBalance;
    }

    @Override
    public int placeDebt(Long partnerId, Order order) {
        Partner partner = getPartner(partnerId);
        int amountToPay = order.getAmountToPay();
        partner.setBalance(partner.getBalance() - amountToPay);
        partnerRepository.save(partner);
        return partner.getBalance();
    }
}
