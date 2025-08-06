package com.example.pos.mapper;

import com.example.pos.dto.response.ProductResponse;
import com.example.pos.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {
    public ProductResponse toResponse(Product product){
        return new ProductResponse(
                product.getId(),
                product.getMarket().getId(),
                product.getName(),
                product.getBarcode(),
                product.getSalesPrice(),
                product.getPurchasePrice(),
                product.getUnitsOfMeasure().name(),
                product.getImageUrl(),
                product.getCategory().name()
        );
    }

    public List<ProductResponse> toResponseList(List<Product> products){
        return products.stream()
                .map(this::toResponse)
                .toList();
    }
}
