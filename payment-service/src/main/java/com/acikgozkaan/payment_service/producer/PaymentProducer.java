package com.acikgozkaan.payment_service.producer;

import com.acikgozkaan.payment_service.config.PaymentMetrics;
import com.acikgozkaan.payment_service.config.RabbitMQProperties;
import com.acikgozkaan.payment_service.dto.PaymentCompletedEvent;
import com.acikgozkaan.payment_service.dto.PaymentFailedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentProducer {

    private static final Logger log = LoggerFactory.getLogger(PaymentProducer.class);
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQProperties properties;
    private final PaymentMetrics paymentMetrics;

    public PaymentProducer(RabbitTemplate rabbitTemplate, RabbitMQProperties properties, PaymentMetrics paymentMetrics) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = properties;
        this.paymentMetrics = paymentMetrics;
    }

    public void sendPaymentCompleted(PaymentCompletedEvent event) {
        rabbitTemplate.convertAndSend(
                properties.exchange(),
                properties.paymentConfirmedRoutingKey(),
                event
        );
        log.info("📤 Sent PaymentCompletedEvent: {}", event);
        paymentMetrics.incrementSuccess();
    }

    public void sendPaymentFailed(PaymentFailedEvent event) {
        rabbitTemplate.convertAndSend(
                properties.exchange(),
                properties.paymentFailedRoutingKey(),
                event
        );
        log.info("📤 Sent PaymentFailedEvent: {}", event);
        paymentMetrics.incrementFailure();
    }
}