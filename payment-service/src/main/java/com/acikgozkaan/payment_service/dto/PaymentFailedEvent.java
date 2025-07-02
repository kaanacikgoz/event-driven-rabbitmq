package com.acikgozkaan.payment_service.dto;

public record PaymentFailedEvent(
        String orderId,
        String reason
) {}