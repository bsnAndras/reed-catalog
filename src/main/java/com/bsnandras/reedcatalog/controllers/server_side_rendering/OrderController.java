package com.bsnandras.reedcatalog.controllers.server_side_rendering;

import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderRequestDto;
import com.bsnandras.reedcatalog.dtos.paymentReceived.PaymentRequestDto;
import com.bsnandras.reedcatalog.dtos.paymentReceived.PaymentResponseDto;
import com.bsnandras.reedcatalog.services.LogService;
import com.bsnandras.reedcatalog.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final LogService logService;

    @PostMapping("/new-order")
    public String placeNewCustomerOrder(@ModelAttribute NewOrderRequestDto requestDto, Model model) {
        System.out.println(orderService.placeNewOrder(requestDto).message());
        return "redirect:/log";
    }

    @GetMapping("/new-payment")
    public String renderNewPaymentForm(Model model, @RequestParam(name = "id") Long orderId) {
        model.addAttribute("order", orderService.getOrderByOrderId(orderId));
        model.addAttribute("requestDto", new PaymentRequestDto(orderId,1,new Date(),""));
        return "pay-order-form";
    }

    @PostMapping("/new-payment")
    public String paymentReceived(@ModelAttribute @Valid PaymentRequestDto requestDto, Model model) {
        //TODO: have some bug to fix: when paying an order with more money than needed, the balance does not update as it should
        PaymentResponseDto response = orderService.updateOrderWithPaymentReceived(requestDto);
        logService.newOrderLog(response);
        System.out.println(response.message());
        return "redirect:/log";
    }
}
