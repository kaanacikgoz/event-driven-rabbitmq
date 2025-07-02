package com.acikgozkaan.payment_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rabbitmq.payment")
public record RabbitMQProperties(
        String exchange,
        String stockReservedQueue,
        String stockReservedRoutingKey,
        String paymentConfirmedRoutingKey,
        String paymentFailedRoutingKey
) {}