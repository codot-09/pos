package com.example.pos.mapper;

import com.example.pos.dto.response.PurchaseResponse;
import com.example.pos.entity.ProductPurchase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PurchaseMapper {
    public PurchaseResponse toResponse(ProductPurchase purchase){
        return new PurchaseResponse(
                purchase.getId(),
                purchase.getPurchaseDate(),
                purchase.getPrice(),
                purchase.getProduct().getId(),
                purchase.getQuantity()
        );
    }

    public List<PurchaseResponse> toResponseList(List<ProductPurchase> purchases){
        return purchases.stream()
                .map(this::toResponse)
                .toList();
    }
}
