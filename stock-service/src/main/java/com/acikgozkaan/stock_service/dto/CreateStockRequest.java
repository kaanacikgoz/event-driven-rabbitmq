package com.acikgozkaan.stock_service.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateStockRequest(

        @NotBlank(message = "Ürün kodu boş olamaz")
        String productCode,

        int quantity
) {}