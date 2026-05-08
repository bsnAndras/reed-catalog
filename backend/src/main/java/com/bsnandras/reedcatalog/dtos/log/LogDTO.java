package com.bsnandras.reedcatalog.dtos.log;

import com.bsnandras.reedcatalog.dtos.links.PartnerLinkDTO;
import com.bsnandras.reedcatalog.models.Log;
import lombok.Builder;

import java.time.Instant;

/**
 * Data Transfer Object for log entries.
 *
 * @param dateTime      The date and time of the log event mapped to local time. Originates from the primary key.
 * @param event         A description of the log event.
 * @param eventId       The ID of the associated order / transaction.
 * @param partnerDTO    The partner associated with the event.
 * @param moneyExchange The amount of money exchanged in the event (if applicable).
 * @param actualBalance The actual balance after the event. <u>Deprecated for removal.</u>
 */
@Builder
public record LogDTO(
        Instant dateTime,
        String event,
        Long eventId,
        PartnerLinkDTO partnerDTO,
        Integer moneyExchange,
        Integer actualBalance //deprecated for removal
) {
    public static LogDTO fromLog(Log log) {
        return LogDTO.builder()
                .dateTime(log.getDateTime())
                .event(log.getEvent())
                .eventId(log.getOrder() != null ? log.getOrder().getId() : null)
                .partnerDTO(PartnerLinkDTO.fromPartner(log.getOrder() != null ? log.getOrder().getPartner() : null))
                .moneyExchange(log.getMoneyExchange())
                .actualBalance(log.getActualBalance())
                .build();
    }
}
