package com.bsnandras.reedcatalog.dtos.links;

import com.bsnandras.reedcatalog.models.Partner;

/**
 * Data Transfer Object for creating links with Partner name and id.
 * @param id Partner.id
 * @param name Partner.name
 */
public record PartnerLinkDTO(
        Long id,
        String name
) {
    public static PartnerLinkDTO fromPartner(Partner partner) {
        if (partner == null) {
            return null;
        }

        return new PartnerLinkDTO(
                partner.getId(),
                partner.getName()
        );
    }
}
