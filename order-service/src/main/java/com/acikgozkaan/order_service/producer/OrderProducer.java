package com.acikgozkaan.order_service.producer;

import com.acikgozkaan.order_service.event.OrderCreatedEvent;

public interface OrderProducer {

    void createOrder(OrderCreatedEvent event);
}