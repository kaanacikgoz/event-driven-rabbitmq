package com.acikgozkaan.order_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rabbitmq.order")
public record RabbitMQProperties(
        String exchange,
        String routingKey,
        String stockRejectedQueue,
        String stockRejectedRoutingKey,
        String paymentConfirmedQueue,
        String paymentConfirmedRoutingKey,
        String paymentFailedQueue,
        String paymentFailedRoutingKey
) {}