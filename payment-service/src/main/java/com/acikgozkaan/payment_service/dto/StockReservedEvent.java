package com.acikgozkaan.payment_service.dto;

public record StockReservedEvent(
        String orderId,
        String message
) {}