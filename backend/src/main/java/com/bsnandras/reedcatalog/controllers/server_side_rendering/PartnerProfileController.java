package com.bsnandras.reedcatalog.controllers.server_side_rendering;

import com.bsnandras.reedcatalog.dtos.links.PartnerLinkDTO;
import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderForm;
import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderRequestDto;
import com.bsnandras.reedcatalog.dtos.payOrder.PaymentForm;
import com.bsnandras.reedcatalog.dtos.payOrder.PaymentRequestDto;
import com.bsnandras.reedcatalog.services.pages.PartnerProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
    public String renderNewOrderForm(Model model, @RequestParam(name = "id") Long partnerId,
                                     @RequestHeader(value = "X-Timezone", required = false, defaultValue = "Europe/Budapest") String timezone) {
        model.addAttribute("partner", PartnerLinkDTO.fromPartner(service.getPartner(partnerId)));
        model.addAttribute("newOrderForm", new NewOrderForm(partnerId, LocalDateTime.now(ZoneId.of(timezone)), timezone, 0, "")
        );

        return "new-order-form";
    }

    @PostMapping("/new-order")
    public String placeNewOrder(@ModelAttribute @Valid NewOrderForm newOrderForm) {

        NewOrderRequestDto requestDto = NewOrderRequestDto.builder()
                .partnerId(newOrderForm.partnerId())
                .dateOfPurchase(ZonedDateTime.of(newOrderForm.dateOfPurchase(), ZoneId.of(newOrderForm.timezone())).toInstant())
                .build();

        service.placeNewOrder(requestDto);

        return "redirect:/log";
    }

    @GetMapping("/pay-order")
    public String renderNewPaymentForm(Model model, @RequestParam(name = "id") Long orderId,
                                       @RequestHeader(value = "X-Timezone", required = false, defaultValue = "Europe/Budapest") String timezone) {
        model.addAttribute("order", service.getOrderByOrderId(orderId));
        model.addAttribute("paymentForm", new PaymentForm(orderId, 0, LocalDateTime.now(ZoneId.of(timezone)), timezone));

        return "pay-order-form";
    }

    @PostMapping("/pay-order")
    public String payOrder(@ModelAttribute @Valid PaymentForm paymentForm) {

        PaymentRequestDto requestDto = PaymentRequestDto.builder()
                .orderId(paymentForm.orderId())
                .paymentAmount(paymentForm.paymentAmount())
                .transactionDateTime(ZonedDateTime.of(paymentForm.transactionDateTime(), ZoneId.of(paymentForm.timezone())).toInstant())
                .build();

        service.payOrder(requestDto);

        return "redirect:/log";
    }
}
