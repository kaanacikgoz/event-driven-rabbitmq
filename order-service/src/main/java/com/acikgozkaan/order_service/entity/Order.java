package com.acikgozkaan.order_service.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String orderId;

    @Column(nullable = false)
    private String productCode;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    public Order() {
    }

    public Order(String orderId, String productCode, int quantity, Status status) {
        this.orderId = orderId;
        this.productCode = productCode;
        this.quantity = quantity;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}