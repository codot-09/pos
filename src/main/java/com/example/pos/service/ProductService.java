package com.example.pos.service;

import com.example.pos.dto.ApiResponse;
import com.example.pos.dto.request.ProductPurchaseRequest;
import com.example.pos.dto.request.ProductRequest;
import com.example.pos.dto.response.ProductResponse;
import com.example.pos.entity.Market;
import com.example.pos.entity.Product;
import com.example.pos.entity.ProductPurchase;
import com.example.pos.entity.enums.ProductCategory;
import com.example.pos.entity.enums.UnitsOfMeasure;
import com.example.pos.exception.DataNotFoundException;
import com.example.pos.mapper.ProductMapper;
import com.example.pos.repository.MarketRepository;
import com.example.pos.repository.ProductPurchaseRepository;
import com.example.pos.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final MarketRepository marketRepository;
    private final ProductMapper mapper;
    private final ProductPurchaseRepository purchaseRepository;

    public ApiResponse<String> addProduct(ProductRequest request, ProductCategory category, UnitsOfMeasure measure){
        if (productRepository.existsByBarcodeAndMarketId(request.getBarcode(),request.getMarketId())){
            return ApiResponse.error("Mahsulot mavjud");
        }
        Market market = marketRepository.findById(request.getMarketId())
                .orElseThrow(() -> new DataNotFoundException("Market topilmadi"));

        if (!market.getOwner().isActive()){
            return ApiResponse.error("Obuna bo'lish talab qilinadi");
        }

        Product product = Product.builder()
                .name(request.getName())
                .barcode(request.getBarcode())
                .category(category)
                .unitsOfMeasure(measure)
                .imageUrl(request.getImageUrl())
                .salesPrice(request.getSalesPrice())
                .purchasePrice(request.getPurchasePrice())
                .quantity(request.getCount())
                .market(market)
                .build();

        productRepository.save(product);

        return ApiResponse.success("Mahsulot qo'shildi");
    }

    public ApiResponse<List<ProductResponse>> findByMarket(UUID marketId){
        List<Product> products = productRepository.findByMarketId(marketId);

        return ApiResponse.success(mapper.toResponseList(products));
    }

    public ApiResponse<ProductResponse> getById(UUID productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Mahsulot topilmadi"));

        return ApiResponse.success(mapper.toResponse(product));
    }

    public ApiResponse<String> updateProduct(ProductRequest request,UUID productId,ProductCategory category,UnitsOfMeasure measure){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Mahsulot topilmadi"));

        product.setName(request.getName());
        product.setBarcode(request.getBarcode());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(category);
        product.setUnitsOfMeasure(measure);
        product.setPurchasePrice(request.getPurchasePrice());
        product.setSalesPrice(request.getSalesPrice());

        productRepository.save(product);

        return ApiResponse.success("Mahsulot tahrirlandi");
    }
}
