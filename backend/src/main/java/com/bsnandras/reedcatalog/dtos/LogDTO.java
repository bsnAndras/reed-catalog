package com.bsnandras.reedcatalog.dtos;

import com.bsnandras.reedcatalog.models.Log;
import lombok.Builder;

import java.util.Date;

@Builder
public record LogDTO(
        Date dateTime,
        String event,
        OrderInfoDto order,
        Integer moneyExchange,
        Integer actualBalance
) {
    public static LogDTO fromLog(Log log) {
        return LogDTO.builder()
                .dateTime(log.getDateTime())
                .event(log.getEvent())
                .order(OrderInfoDto.fromOrder(log.getOrder()))
                .moneyExchange(log.getMoneyExchange())
                .actualBalance(log.getActualBalance())
                .build();
    }
}
