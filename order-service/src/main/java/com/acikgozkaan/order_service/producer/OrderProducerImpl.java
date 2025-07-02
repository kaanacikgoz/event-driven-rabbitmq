package com.acikgozkaan.order_service.producer;

import com.acikgozkaan.order_service.config.OrderMetrics;
import com.acikgozkaan.order_service.config.RabbitMQProperties;
import com.acikgozkaan.order_service.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProducerImpl implements OrderProducer {

    private static final Logger log = LoggerFactory.getLogger(OrderProducerImpl.class);
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQProperties properties;
    private final OrderMetrics orderMetrics;

    public OrderProducerImpl(RabbitTemplate rabbitTemplate, RabbitMQProperties properties, OrderMetrics orderMetrics) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = properties;
        this.orderMetrics = orderMetrics;
    }

    public void createOrder(OrderCreatedEvent event) {
        rabbitTemplate.convertAndSend(
                properties.exchange(),
                properties.routingKey(),
                event
        );
        log.info("📤 OrderCreatedEvent gönderildi: {}", event);
        orderMetrics.incrementOrderCreated();
    }
}