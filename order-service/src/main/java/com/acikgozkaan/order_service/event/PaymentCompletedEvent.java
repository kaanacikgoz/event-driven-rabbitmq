package com.acikgozkaan.order_service.event;

public record PaymentCompletedEvent(
        String orderId,
        String message
) {}