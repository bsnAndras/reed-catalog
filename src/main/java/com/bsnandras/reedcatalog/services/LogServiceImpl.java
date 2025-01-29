package com.bsnandras.reedcatalog.services;

import com.bsnandras.reedcatalog.models.Log;
import com.bsnandras.reedcatalog.models.Order;
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
    public Log newLog(Order newOrder) {
        Log lastLog = showHistory().getFirst();
        Log newLog = Log.builder()
                .dateTime(new Date())
                .event("New Order placed")
                .order(newOrder)
                .actualBalance(lastLog.getActualBalance())
                .build();
        return save(newLog);
    }
}
