package com.bsnandras.reedcatalog.services.database;

import com.bsnandras.reedcatalog.dtos.log.LogDTO;
import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderResponseDto;
import com.bsnandras.reedcatalog.dtos.payOrder.PaymentResponseDto;
import com.bsnandras.reedcatalog.models.Log;

import java.util.List;

public interface LogService {
    Log save(Log log);

    List<LogDTO> showHistory();

    Log newOrderLog(NewOrderResponseDto response);

    Log newOrderLog(PaymentResponseDto response);
}
