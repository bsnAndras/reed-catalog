package com.bsnandras.reedcatalog.controllers.api;

import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderRequestDto;
import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderResponseDto;
import com.bsnandras.reedcatalog.dtos.paymentReceived.PaymentRequestDto;
import com.bsnandras.reedcatalog.dtos.paymentReceived.PaymentResponseDto;
import com.bsnandras.reedcatalog.services.LogService;
import com.bsnandras.reedcatalog.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class ApiOrderController {
    private final OrderService orderService;
    private final LogService logService;

    @PostMapping("/new-order")
    public ResponseEntity<NewOrderResponseDto> placeNewCustomerOrder(@RequestBody NewOrderRequestDto requestDto) {
        NewOrderResponseDto responseDto = orderService.placeNewOrder(requestDto);
        System.out.println(responseDto.message());
        return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
    }

    @PostMapping("/new-payment")
    public ResponseEntity<?> paymentReceived(@RequestBody @Valid PaymentRequestDto requestDto) {
        //TODO: have some bug to fix: when paying an order with more money than needed, the balance does not update as it should
        PaymentResponseDto response = orderService.updateOrderWithPaymentReceived(requestDto);
        logService.newOrderLog(response);
        System.out.println(response.message());
        return new ResponseEntity<>(response.message(),HttpStatus.OK);
    }
}
