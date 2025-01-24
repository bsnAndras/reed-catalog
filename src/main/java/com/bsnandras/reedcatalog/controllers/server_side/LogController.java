package com.bsnandras.reedcatalog.controllers.server_side;

import com.bsnandras.reedcatalog.models.Log;
import com.bsnandras.reedcatalog.models.Order;
import com.bsnandras.reedcatalog.repositories.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LogController {
    private final LogRepository logRepository;
    @GetMapping({"/","/log"})
    public String getLog(Model model) {
        List<Log> logList = logRepository.findAll();

        model.addAttribute("firstLog", logList.getFirst());
        model.addAttribute("logList", logList);
        return "index";
    }
}
