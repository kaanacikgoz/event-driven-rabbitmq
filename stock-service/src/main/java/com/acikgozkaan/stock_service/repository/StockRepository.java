package com.acikgozkaan.stock_service.repository;

import com.acikgozkaan.stock_service.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StockRepository extends JpaRepository<Stock, UUID> {

    Optional<Stock> findByProductCode(String productCode);
}