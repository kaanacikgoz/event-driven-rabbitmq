package com.acikgozkaan.stock_service.service;

import com.acikgozkaan.stock_service.dto.CreateStockRequest;
import com.acikgozkaan.stock_service.entity.Stock;
import com.acikgozkaan.stock_service.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockRequest request) {
        boolean exists = stockRepository.findByProductCode(request.productCode()).isPresent();

        if (exists) {
            throw new IllegalArgumentException("Bu ürün zaten stokta mevcut: " + request.productCode());
        }

        Stock stock = new Stock(
                request.productCode(),
                request.quantity()
        );

        stockRepository.save(stock);
    }

}