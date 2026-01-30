package com.bsnandras.reedcatalog.services.pages;

import com.bsnandras.reedcatalog.models.Partner;

import java.util.List;

public interface PartnerListService {

    List<Partner> getAllPartners();

    boolean addPartner(Partner partner);
}
