package com.bsnandras.reedcatalog.controllers.api;

import com.bsnandras.reedcatalog.dtos.log.LogDTO;
import com.bsnandras.reedcatalog.services.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiLogController {
    private final LogService logService;

    @GetMapping("/log")
    public ResponseEntity<List<LogDTO>> getLog() {
        return ResponseEntity.ok(logService.showHistory());
    }
}
