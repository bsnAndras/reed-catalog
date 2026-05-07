package com.bsnandras.reedcatalog.controllers.server_side_rendering;

import com.bsnandras.reedcatalog.dtos.links.PartnerLinkDTO;
import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderRequestDto;
import com.bsnandras.reedcatalog.dtos.paymentReceived.PaymentRequestDto;
import com.bsnandras.reedcatalog.services.pages.PartnerProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequiredArgsConstructor
@RequestMapping("/partner")
public class PartnerProfileController {

    private final PartnerProfileService service;

    @GetMapping("/profile")
    public String renderPartnerPage(Model model, @RequestParam(name = "id") Long partnerId) {

        model.addAttribute("partner", service.getPartnerPageData(partnerId));

        return "partner";
    }

    @GetMapping("/new-order")
    public String renderNewOrderForm(Model model, @RequestParam(name = "id") Long partnerId) {
        model.addAttribute("partner", PartnerLinkDTO.fromPartner(service.getPartner(partnerId)));
        model.addAttribute("requestDto", NewOrderRequestDto.builder()
                .partnerId(partnerId)
                .build()
        );

        return "new-order-form";
    }

    @PostMapping("/new-order")
    public String placeNewOrder(@ModelAttribute NewOrderRequestDto requestDto) {
        service.placeNewOrder(requestDto);
        return "redirect:/log";
    }

    @GetMapping("/pay-order")
    public String renderNewPaymentForm(Model model, @RequestParam(name = "id") Long orderId) {
        model.addAttribute("order", service.getOrderByOrderId(orderId));
        model.addAttribute("requestDto", new PaymentRequestDto(orderId, 1, new Date()));
        return "pay-order-form";
    }

    @PostMapping("/pay-order")
    public String payOrder(@ModelAttribute @Valid PaymentRequestDto requestDto) {
        service.payOrder(requestDto);
        return "redirect:/log";
    }
}
