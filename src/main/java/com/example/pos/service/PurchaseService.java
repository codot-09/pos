package com.example.pos.service;

import com.example.pos.dto.ApiResponse;
import com.example.pos.dto.request.ProductPurchaseRequest;
import com.example.pos.dto.response.PurchaseResponse;
import com.example.pos.entity.Product;
import com.example.pos.entity.ProductPurchase;
import com.example.pos.exception.DataNotFoundException;
import com.example.pos.mapper.PurchaseMapper;
import com.example.pos.repository.ProductPurchaseRepository;
import com.example.pos.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final ProductPurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final PurchaseMapper mapper;

    @Transactional
    public ApiResponse<String> purchaseProduct(ProductPurchaseRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Mahsulot topilmadi"));

        ProductPurchase purchase = ProductPurchase.builder()
                .product(product)
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .build();

        purchaseRepository.save(purchase);

        double newTotalCount = product.getQuantity() + request.getQuantity();
        product.setQuantity(newTotalCount);

        product.setPurchasePrice(request.getPrice());

        productRepository.save(product);

        return ApiResponse.success("Mahsulot kirimi qo‘shildi");
    }

    public ApiResponse<List<PurchaseResponse>> getByMarket(UUID marketId){
        List<ProductPurchase> purchases = purchaseRepository.findByMarket(marketId);

        return ApiResponse.success(mapper.toResponseList(purchases));
    }

    public ApiResponse<List<PurchaseResponse>> getByProduct(UUID productId){
        List<ProductPurchase> purchases = purchaseRepository.findByProductId(productId);

        return ApiResponse.success(mapper.toResponseList(purchases));
    }
}
