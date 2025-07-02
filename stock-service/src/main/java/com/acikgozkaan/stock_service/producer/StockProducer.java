package com.acikgozkaan.stock_service.producer;

import com.acikgozkaan.stock_service.event.StockRejectedEvent;
import com.acikgozkaan.stock_service.event.StockReservedEvent;

public interface StockProducer {

    void sendStockReservedEvent(StockReservedEvent event);
    void sendStockRejectedEvent(StockRejectedEvent event);
}