package com.acikgozkaan.order_service.event;

public record StockRejectedEvent(
        String orderId,
        String reason
) {}