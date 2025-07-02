package com.acikgozkaan.order_service.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class OrderMetrics {

    private final Counter orderCreatedCounter;
    private final Counter dlqOrderCounter;

    public OrderMetrics(MeterRegistry registry) {
        this.orderCreatedCounter = Counter.builder("order_created_total")
                .description("Toplam oluşturulan sipariş sayısı")
                .register(registry);
        this.dlqOrderCounter = Counter.builder("order_dlq_total")
                .description("DLQ'den alınan sipariş sayısı")
                .register(registry);
    }

    public void incrementOrderCreated() {
        orderCreatedCounter.increment();
    }

    public void incrementDlqOrders() {
        dlqOrderCounter.increment();
    }

}