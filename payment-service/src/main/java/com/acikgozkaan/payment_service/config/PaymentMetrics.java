package com.acikgozkaan.payment_service.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class PaymentMetrics {

    private final Counter paymentSuccessCounter;
    private final Counter paymentFailedCounter;

    public PaymentMetrics(MeterRegistry registry) {
        this.paymentSuccessCounter = Counter.builder("payment_success_total")
                .description("Başarılı ödemelerin sayısı")
                .register(registry);
        this.paymentFailedCounter = Counter.builder("payment_failed_total")
                .description("Başarısız ödemelerin sayısı")
                .register(registry);
    }

    public void incrementSuccess() {
        paymentSuccessCounter.increment();
    }

    public void incrementFailure() {
        paymentFailedCounter.increment();
    }

}