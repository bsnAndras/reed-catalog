package com.bsnandras.reedcatalog.services;

import com.bsnandras.reedcatalog.dtos.paymentReceived.PaymentResponseDto;
import com.bsnandras.reedcatalog.models.Log;
import com.bsnandras.reedcatalog.models.Order;

import java.util.List;

public interface LogService {
    Log save(Log log);

    List<Log> showHistory();

    Log newOrderLog(Order newOrder);

    Log newOrderLog(PaymentResponseDto response);
}
