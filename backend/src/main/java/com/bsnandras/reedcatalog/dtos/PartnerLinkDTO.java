package com.bsnandras.reedcatalog.dtos;

import com.bsnandras.reedcatalog.models.Partner;

public record PartnerLinkDTO(
        Long id,
        String name) {
    public static PartnerLinkDTO fromPartner(Partner partner) {
        if(partner == null) {
            return null;
        }

        return new PartnerLinkDTO(
                partner.getId(),
                partner.getName()
        );
    }
}
