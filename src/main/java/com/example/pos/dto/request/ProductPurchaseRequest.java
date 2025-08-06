package com.example.pos.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ProductPurchaseRequest {
    private UUID productId;
    private Double quantity;
    private BigDecimal price;
}

