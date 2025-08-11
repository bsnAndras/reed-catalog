package com.bsnandras.reedcatalog.controllers.server_side_rendering;

import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderRequestDto;
import com.bsnandras.reedcatalog.models.Partner;
import com.bsnandras.reedcatalog.services.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/partner")
public class PartnerController {
    private final PartnerService partnerService;
    @GetMapping("/profile")
    public String renderPartnerPage(Model model, @RequestParam(name = "id") Long partnerId) {

        model.addAttribute("partner", partnerService.getPartnerPageData(partnerId));

        return "partner";
    }

    @GetMapping("/partner-list")
    public String getAllPartners(Model model) {
        List<Partner> partnerList = partnerService.showAllPartners();

        model.addAttribute("partnerList", partnerList);

        return "partner-list";
    }

    @GetMapping("/new-order")
    public String renderPlaceNewOrderForm(Model model, @RequestParam(name = "id") Long partnerId){
        model.addAttribute("partner", partnerService.getPartner(partnerId));
        model.addAttribute("requestDto", new NewOrderRequestDto(partnerId,0));

        return "new-order-form";
    }
}
