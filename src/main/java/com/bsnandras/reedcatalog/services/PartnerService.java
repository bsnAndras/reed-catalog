package com.bsnandras.reedcatalog.services;

import com.bsnandras.reedcatalog.dtos.PartnerPageResponseDto;
import com.bsnandras.reedcatalog.models.Order;
import com.bsnandras.reedcatalog.models.Partner;

import java.util.List;

public interface PartnerService {
    Partner getPartner(Long id);

    List<Order> getOrderHistory(Long partnerId);

    List<Partner> showAllPartners();

    PartnerPageResponseDto getPartnerPageData(Long partnerId);

    int setBalance(Long partnerId, int newBalance);

    /**
     * Method for placing debt on partner account
     * @param partnerId partner's id
     * @param order the order to be paid, when money is available
     * @return the new account balance of the partner
     */
    int placeDebt(Long partnerId, Order order);
}
