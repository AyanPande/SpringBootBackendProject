package com.electronic.store.controllers;

import com.electronic.store.dtos.CartDto;
import com.electronic.store.dtos.CreateOrderRequest;
import com.electronic.store.dtos.OrderConfirmationDto;
import com.electronic.store.dtos.OrderDto;
import com.electronic.store.services.CartService;
import com.electronic.store.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/{userId}")
    public ResponseEntity<OrderDto> createOrder(
            @RequestBody OrderDto orderDto,
            @PathVariable("userId") String userId) {

        OrderDto order = orderService.createOrder(orderDto, userId);
        return new ResponseEntity<>(order, HttpStatus.CREATED);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderDto>> findOrderByUser(
            @PathVariable("userId") String userId
    ) {

        List<OrderDto> orders = orderService.findOrderByUser(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> findAllOrder() {
        List<OrderDto> allOrder = orderService.findAllOrder();
        return new ResponseEntity<>(allOrder, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<OrderDto> updateOrder(
            @RequestBody OrderConfirmationDto orderConfirmationDto,
            @PathVariable("userId") String userId
            ) {
        OrderDto orderDto = orderService.updateStatusOfOrder(orderConfirmationDto, userId);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }
}
