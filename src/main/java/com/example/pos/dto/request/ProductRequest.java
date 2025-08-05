package com.example.pos.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequest {
    private String name;
    private String barcode;
    private String imageUrl;
    private BigDecimal purchasePrice;
    private BigDecimal salesPrice;
}
