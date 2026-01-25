package com.bsnandras.reedcatalog.services;

import com.bsnandras.reedcatalog.models.Partner;

import java.util.List;

public interface PartnerListService {
    Partner getPartner(Long id);

    List<Partner> getAllPartners();

    boolean addPartner(Partner partner);
}
