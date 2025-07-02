package com.acikgozkaan.stock_service.controller;

import com.acikgozkaan.stock_service.dto.CreateStockRequest;
import com.acikgozkaan.stock_service.service.StockService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<String> createStock(@Valid @RequestBody CreateStockRequest request) {
        stockService.createStock(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Stok başarıyla eklendi: " + request.productCode());
    }

}