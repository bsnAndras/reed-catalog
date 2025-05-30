package com.bsnandras.reedcatalog.services;

import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderResponseDto;
import com.bsnandras.reedcatalog.dtos.paymentReceived.PaymentResponseDto;
import com.bsnandras.reedcatalog.models.Log;
import com.bsnandras.reedcatalog.repositories.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {
    private final LogRepository logRepository;

    @Override
    public Log save(Log log) {
        return logRepository.save(log);
    }

    @Override
    public List<Log> showHistory() {
        return logRepository.findAllByOrderByDateTimeDesc();
    }

    @Override
    public Log newOrderLog(NewOrderResponseDto response) {
        Log lastLog = showHistory().getFirst();
        Log newLog = Log.builder()
                .dateTime(new Date())
                .event(response.message())
                .order(response.order())
                .actualBalance(lastLog.getActualBalance())
                .build();
        return save(newLog);
    }

    @Override
    public Log newOrderLog(PaymentResponseDto response) {
        Log lastLog = showHistory().getFirst();
        int paymentAmount = response.moneyPaid();
        Log newLog = Log.builder()
                .dateTime(new Date())
                .event(response.message())
                .order(response.updatedOrder())
                .moneyExchange(paymentAmount)
                .actualBalance(lastLog.getActualBalance() + paymentAmount)
                .build();
        return save(newLog);
    }
}
