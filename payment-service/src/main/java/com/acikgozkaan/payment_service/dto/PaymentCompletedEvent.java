package com.acikgozkaan.payment_service.dto;

public record PaymentCompletedEvent(
        String orderId,
        String message
) {}