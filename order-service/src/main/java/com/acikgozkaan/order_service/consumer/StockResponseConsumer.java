package com.acikgozkaan.order_service.consumer;

import com.acikgozkaan.order_service.config.OrderMetrics;
import com.acikgozkaan.order_service.entity.Order;
import com.acikgozkaan.order_service.entity.Status;
import com.acikgozkaan.order_service.event.*;
import com.acikgozkaan.order_service.exception.OrderNotFoundException;
import com.acikgozkaan.order_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class StockResponseConsumer {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final OrderRepository orderRepository;
    private final OrderMetrics orderMetrics;

    public StockResponseConsumer(OrderRepository orderRepository, OrderMetrics orderMetrics) {
        this.orderRepository = orderRepository;
        this.orderMetrics = orderMetrics;
    }

    @RabbitListener(queues = "order-created-dlq")
    public void handleFailedOrder(OrderCreatedEvent event) {
        log.warn("🚫 DLQ kuyruğundan alınan başarısız sipariş: {}", event.orderId());
        orderRepository.findByOrderId(event.orderId())
                .ifPresent(order -> {
                    order.setStatus(Status.REJECTED);
                    orderRepository.save(order);
                });
        orderMetrics.incrementDlqOrders();
    }

    @Transactional
    @RabbitListener(queues = "stock-rejected-queue")
    public void handleStockRejected(StockRejectedEvent event) {
        log.info("[stock-rejected] Event alındı: {}", event);

        var order = orderRepository.findByOrderId(event.orderId())
                .orElseThrow(() -> new OrderNotFoundException(event.orderId()));

        order.setStatus(Status.REJECTED);
        log.info("Sipariş durumu REJECTED olarak güncellendi: {}", order.getOrderId());
    }

    @Transactional
    @RabbitListener(queues = "payment-confirmed-queue")
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        log.info("💰 Payment completed for order: {}", event.orderId());

        Order order = orderRepository.findByOrderId(event.orderId())
                .orElseThrow(() -> new OrderNotFoundException(event.orderId()));

        order.setStatus(Status.CONFIRMED);
        log.info("✅ Order marked as COMPLETED: {}", order.getOrderId());
    }

    @Transactional
    @RabbitListener(queues = "payment-failed-queue")
    public void handlePaymentFailed(PaymentFailedEvent event) {
        log.warn("💸 Payment failed for order: {}", event.orderId());

        Order order = orderRepository.findByOrderId(event.orderId())
                .orElseThrow(() -> new OrderNotFoundException(event.orderId()));

        order.setStatus(Status.REJECTED);
        log.warn("🚫 Order marked as REJECTED: {}", order.getOrderId());
    }

}