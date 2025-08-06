package com.example.pos.mapper;

import com.example.pos.dto.response.SalesResponse;
import com.example.pos.entity.Product;
import com.example.pos.entity.Sales;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SalesMapper {
    private final ProductMapper mapper;

    public SalesResponse toResponse(Sales sales){
        List<Product> products = sales.getProducts();
        return new SalesResponse(
                sales.getId(),
                sales.getSalesDate(),
                sales.getTotalPrice(),
                sales.getPayedPrice(),
                sales.isPaid(),
                mapper.toResponseList(products)
        );
    }
}
