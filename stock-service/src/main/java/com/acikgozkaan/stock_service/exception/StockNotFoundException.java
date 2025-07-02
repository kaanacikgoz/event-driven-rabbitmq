package com.acikgozkaan.stock_service.exception;

public class StockNotFoundException extends RuntimeException {
    public StockNotFoundException(String productCode) {
        super("Stok bulunamadı: " + productCode);
    }
}