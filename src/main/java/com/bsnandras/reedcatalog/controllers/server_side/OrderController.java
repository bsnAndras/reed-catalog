package com.bsnandras.reedcatalog.controllers.server_side;

import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderRequestDto;
import com.bsnandras.reedcatalog.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/new-order")
    public String placeNewCustomerOrder(@RequestBody NewOrderRequestDto requestDto) {
        System.out.println(orderService.placeNewOrder(requestDto).message());
        return "redirect:/log";
    }
}
