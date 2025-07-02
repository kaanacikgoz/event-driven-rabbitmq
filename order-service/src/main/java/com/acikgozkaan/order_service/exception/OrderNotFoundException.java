package com.acikgozkaan.order_service.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String orderId) {
        super("Sipariş bulunamadı: " + orderId);
    }
}