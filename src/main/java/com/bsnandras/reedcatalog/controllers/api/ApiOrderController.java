package com.bsnandras.reedcatalog.controllers.api;

import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderRequestDto;
import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderResponseDto;
import com.bsnandras.reedcatalog.services.OrderService;
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

    @PostMapping("/new-order")
    public ResponseEntity<NewOrderResponseDto> placeNewCustomerOrder(@RequestBody NewOrderRequestDto requestDto) {
        NewOrderResponseDto responseDto = orderService.placeNewOrder(requestDto);
        System.out.println(responseDto.message());
        return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
    }
}
