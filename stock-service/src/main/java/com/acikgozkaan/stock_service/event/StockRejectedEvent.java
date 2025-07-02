package com.acikgozkaan.stock_service.event;

public record StockRejectedEvent(
        String orderId,
        String reason
) {}