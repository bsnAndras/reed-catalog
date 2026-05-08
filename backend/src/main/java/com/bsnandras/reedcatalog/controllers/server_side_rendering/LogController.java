package com.bsnandras.reedcatalog.controllers.server_side_rendering;

import com.bsnandras.reedcatalog.dtos.log.LogDTO;
import com.bsnandras.reedcatalog.services.database.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LogController {
    private final LogService logService;

    @GetMapping({"/", "/log"})
    public String getLog(Model model,
                         @RequestHeader(value = "X-Timezone", required = false, defaultValue = "Europe/Budapest") String timezone) {
        List<LogDTO> logList = logService.showHistory();

        model.addAttribute("logList", logList);
        model.addAttribute("timezone", timezone);
        return "log";
    }
}
