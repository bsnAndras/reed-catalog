package com.bsnandras.reedcatalog.services;

import com.bsnandras.reedcatalog.models.Log;
import com.bsnandras.reedcatalog.repositories.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService{
    private final LogRepository logRepository;
    @Override
    public List<Log> showHistory() {
        return logRepository.findAllByOrderByDateTimeDesc();
    }
}
