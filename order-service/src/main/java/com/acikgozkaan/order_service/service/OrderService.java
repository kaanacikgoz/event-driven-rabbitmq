package com.acikgozkaan.order_service.service;

import com.acikgozkaan.order_service.entity.Order;
import com.acikgozkaan.order_service.entity.Status;
import com.acikgozkaan.order_service.event.OrderCreatedEvent;
import com.acikgozkaan.order_service.producer.OrderProducerImpl;
import com.acikgozkaan.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducerImpl orderCreatedProducer;

    public OrderService(OrderRepository orderRepository, OrderProducerImpl orderCreatedProducer) {
        this.orderRepository = orderRepository;
        this.orderCreatedProducer = orderCreatedProducer;
    }

    public void createOrder(OrderCreatedEvent event) {
        isExistOrder(event);
        Order order = new Order(
                event.orderId(),
                event.productCode(),
                event.quantity(),
                Status.PENDING
        );
        orderRepository.save(order);
        orderCreatedProducer.createOrder(event);
    }

    public void isExistOrder(OrderCreatedEvent event) {
        if (orderRepository.existsByOrderId(event.orderId())) {
            throw new IllegalArgumentException("Bu orderId zaten mevcut: " + event.orderId());
        }
    }


}