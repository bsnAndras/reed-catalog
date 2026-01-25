package com.bsnandras.reedcatalog.controllers.server_side_rendering;

import com.bsnandras.reedcatalog.models.Partner;
import com.bsnandras.reedcatalog.services.pages.PartnerListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/partner-list")
public class PartnerListController {

    private final PartnerListService partnerListService;

    @GetMapping("/")
    public String getAllPartners(Model model) {
        List<Partner> partnerList = partnerListService.getAllPartners();

        model.addAttribute("partnerList", partnerList);

        return "partner-list";
    }
}
