package com.acikgozkaan.stock_service.consumer;

import com.acikgozkaan.stock_service.event.OrderCreatedEvent;
import com.acikgozkaan.stock_service.event.StockRejectedEvent;
import com.acikgozkaan.stock_service.event.StockReservedEvent;
import com.acikgozkaan.stock_service.producer.StockEventProducer;
import com.acikgozkaan.stock_service.repository.StockRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class StockConsumer {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final StockRepository stockRepository;
    private final StockEventProducer stockEventProducer;

    public StockConsumer(StockRepository stockRepository, StockEventProducer stockEventProducer) {
        this.stockRepository = stockRepository;
        this.stockEventProducer = stockEventProducer;
    }

    @Transactional
    @RabbitListener(queues = "order-created-queue", errorHandler = "stockErrorHandler")
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("Yeni sipariş alındı: {}", event);

        stockRepository.findByProductCode(event.productCode()).ifPresentOrElse(stock -> {
            int current = stock.getQuantity();
            int required = event.quantity();

            if (current < required) {
                log.warn("Yetersiz stok: {} | Gerekli: {}, Mevcut: {}", event.productCode(), required, current);
                stockEventProducer.sendStockRejectedEvent(
                        new StockRejectedEvent(
                                event.orderId(),
                                "Stok yetersiz: " + event.productCode()
                        )
                );
            } else {
                stock.setQuantity(current - required);
                log.info("Stok ayrıldı: {} | Kalan: {}", event.productCode(), stock.getQuantity());
                stockEventProducer.sendStockReservedEvent(
                        new StockReservedEvent(
                                event.orderId(),
                                "Stok ayrıldı: " + event.productCode()
                        )
                );
            }
        }, () -> {
            log.error("Stok bulunamadı: {}", event.productCode());

            stockEventProducer.sendStockRejectedEvent(
                    new StockRejectedEvent(
                            event.orderId(),
                            "Ürün bulunamadı: " + event.productCode()
                    )
            );

            throw new AmqpRejectAndDontRequeueException("Ürün bulunamadı: " + event.productCode());
        });
    }

}