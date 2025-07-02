package com.acikgozkaan.stock_service.event;

public record OrderCreatedEvent(
        String orderId,
        String productCode,
        int quantity
) {}