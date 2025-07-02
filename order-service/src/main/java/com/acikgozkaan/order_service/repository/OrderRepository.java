package com.acikgozkaan.order_service.repository;

import com.acikgozkaan.order_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    Optional<Order> findByOrderId(String orderId);
    boolean existsByOrderId(String orderId);
}