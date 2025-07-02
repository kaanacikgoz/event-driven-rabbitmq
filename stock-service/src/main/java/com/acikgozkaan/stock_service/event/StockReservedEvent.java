package com.acikgozkaan.stock_service.event;

public record StockReservedEvent(
        String orderId,
        String message
) {}