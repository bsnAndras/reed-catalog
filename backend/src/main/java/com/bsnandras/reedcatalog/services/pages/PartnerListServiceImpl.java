package com.bsnandras.reedcatalog.services.pages;

import com.bsnandras.reedcatalog.models.Partner;
import com.bsnandras.reedcatalog.repositories.PartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.rmi.AlreadyBoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerListServiceImpl implements PartnerListService {

    private final PartnerRepository partnerRepository;

    @Override
    public Partner getPartner(Long id) {
        return partnerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Partner> getAllPartners() {
        return partnerRepository.findAllByOrderByName();
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
}
