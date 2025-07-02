package com.acikgozkaan.order_service.event;

public record OrderCreatedEvent(
        String orderId,
        String productCode,
        int quantity
) {}