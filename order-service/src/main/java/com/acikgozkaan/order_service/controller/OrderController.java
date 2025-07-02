package com.acikgozkaan.order_service.controller;

import com.acikgozkaan.order_service.event.OrderCreatedEvent;
import com.acikgozkaan.order_service.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@Valid @RequestBody OrderCreatedEvent event) {
        orderService.createOrder(event);
        return ResponseEntity.ok("Sipariş alındı ve mesaj gönderildi: " + event.orderId());
    }
}