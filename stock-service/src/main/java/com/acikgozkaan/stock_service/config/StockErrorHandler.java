package com.acikgozkaan.stock_service.config;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component("stockErrorHandler")
public class StockErrorHandler implements RabbitListenerErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(StockErrorHandler.class);

    @Override
    public Object handleError(org.springframework.amqp.core.Message message, Channel channel, Message<?> message1, ListenerExecutionFailedException e) throws Exception {
        assert message1 != null;
        log.warn("💥 Mesaj işlenemedi! Payload: {}", message1.getPayload());
        log.warn("Sebep: {}", e.getCause().getMessage());
        return null;
    }
}