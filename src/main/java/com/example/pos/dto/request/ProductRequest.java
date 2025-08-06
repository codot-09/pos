package com.example.pos.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ProductRequest {
    private String name;
    private String barcode;
    private String imageUrl;
    private Double count;
    private BigDecimal purchasePrice;
    private BigDecimal salesPrice;
    private UUID marketId;
}
