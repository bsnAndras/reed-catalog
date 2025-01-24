package com.bsnandras.reedcatalog.controllers.server_side;

import com.bsnandras.reedcatalog.models.Log;
import com.bsnandras.reedcatalog.services.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LogController {
    private final LogService logService;

    @GetMapping({"/", "/log"})
    public String getLog(Model model) {
        List<Log> logList = logService.showHistory();

        model.addAttribute("logList", logList);
        return "log";
    }
}
