package com.acikgozkaan.order_service.event;

public record PaymentFailedEvent(
        String orderId,
        String reason
) {}