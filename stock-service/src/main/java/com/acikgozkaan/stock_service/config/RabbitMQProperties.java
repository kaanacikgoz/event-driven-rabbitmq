package com.acikgozkaan.stock_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rabbitmq.stock")
public record RabbitMQProperties(
        String exchange,
        String deadLetterExchange,
        String orderCreatedQueue,
        String OrderCreatedDlqQueue,
        String orderCreatedRoutingKey,
        String orderCreatedDlqRoutingKey,
        String reservedRoutingKey,
        String rejectedRoutingKey
) {}