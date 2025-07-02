package com.acikgozkaan.payment_service.consumer;

import com.acikgozkaan.payment_service.config.RabbitMQProperties;
import com.acikgozkaan.payment_service.dto.StockReservedEvent;
import com.acikgozkaan.payment_service.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class StockReservedConsumer {

    private final static Logger log = LoggerFactory.getLogger(StockReservedConsumer.class);
    private final PaymentService paymentService;

    public StockReservedConsumer(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RabbitListener(queues = "stock-reserved-queue")
    public void handle(StockReservedEvent event) {
        log.info("💵 Stock reserved, starting payment for order: {}", event.orderId());
        paymentService.processPayment(event.orderId());
    }
}