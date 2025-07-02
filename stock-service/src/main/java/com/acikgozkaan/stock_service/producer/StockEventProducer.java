package com.acikgozkaan.stock_service.producer;

import com.acikgozkaan.stock_service.config.RabbitMQProperties;
import com.acikgozkaan.stock_service.event.StockRejectedEvent;
import com.acikgozkaan.stock_service.event.StockReservedEvent;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;

@Component
public class StockEventProducer implements StockProducer {

    private final Logger log = LoggerFactory.getLogger(StockEventProducer.class);
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQProperties properties;

    public StockEventProducer(RabbitTemplate rabbitTemplate, RabbitMQProperties properties) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = properties;
    }

    @Override
    public void sendStockReservedEvent(StockReservedEvent event) {
        rabbitTemplate.convertAndSend(
                properties.exchange(),
                properties.reservedRoutingKey(),
                event
        );
        log.info("📤 StockReservedEvent gönderildi: {}", event);
    }

    @Override
    public void sendStockRejectedEvent(StockRejectedEvent event) {
        rabbitTemplate.convertAndSend(
                properties.exchange(),
                properties.rejectedRoutingKey(),
                event
        );
        log.info("📤 StockRejectedEvent gönderildi: {}", event);
    }
}