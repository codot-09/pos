package com.example.pos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private UUID id;
    private UUID marketId;
    private String name;
    private String barcode;
    private BigDecimal salesPrice;
    private BigDecimal purchasePrice;
    private String measure;
    private String imageUrl;
    private String category;
}
