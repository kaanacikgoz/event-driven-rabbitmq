package com.acikgozkaan.order_service.event;

public record StockReservedEvent(
        String orderId,
        String message
) {}