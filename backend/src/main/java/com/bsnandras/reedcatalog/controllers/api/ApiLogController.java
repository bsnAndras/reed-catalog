package com.bsnandras.reedcatalog.controllers.api;

import com.bsnandras.reedcatalog.services.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiLogController {
    private final LogService logService;

    @GetMapping("/log")
    public ResponseEntity<?> getLog() {
        return ResponseEntity.ok(logService.showHistory());
    }
}
