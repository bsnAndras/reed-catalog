package com.bsnandras.reedcatalog.controllers.server_side_rendering;

import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderRequestDto;
import com.bsnandras.reedcatalog.dtos.paymentReceived.PaymentRequestDto;
import com.bsnandras.reedcatalog.dtos.paymentReceived.PaymentResponseDto;
import com.bsnandras.reedcatalog.services.LogService;
import com.bsnandras.reedcatalog.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final LogService logService;

    @PostMapping("/new-order")
    public String placeNewCustomerOrder(@RequestBody NewOrderRequestDto requestDto) {
        System.out.println(orderService.placeNewOrder(requestDto).message());
        return "redirect:/log";
    }

    @PatchMapping("/new-payment")
    public String paymentReceived(@RequestBody @Valid PaymentRequestDto requestDto) {
        PaymentResponseDto response = orderService.updateOrderWithPaymentReceived(requestDto);
        logService.newOrderLog(response);
        System.out.println(response.message());
        return "redirect:/log";
    }
}
