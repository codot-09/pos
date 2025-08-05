//package com.example.pos.service;
//
//import com.example.pos.dto.ApiResponse;
//import com.example.pos.dto.request.ProductRequest;
//import com.example.pos.entity.Product;
//import com.example.pos.entity.enums.ProductCategory;
//import com.example.pos.entity.enums.UnitsOfMeasure;
//import com.example.pos.repository.ProductRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class ProductService {
//    private final ProductRepository productRepository;
//
//    public ApiResponse<String> addProduct(ProductRequest request, ProductCategory category, UnitsOfMeasure measure){
//        if (productRepository.existsByBarcode(request.getBarcode())){
//            return ApiResponse.error("Mahsulot mavjud");
//        }
//
//        Product product = Product.builder()
//                .name(request.getName())
//                .barcode(request.getBarcode())
//                .category(category)
//                .unitsOfMeasure(measure)
//                .imageUrl(request.getImageUrl())
//                .salesPrice(request.getSalesPrice())
//                .purchasePrice(request.getPurchasePrice())
//                .build();
//
//        productRepository.save(product);
//
//        return ApiResponse.success("Mahsulot qo'shildi");
//    }
//
//    public ApiResponse<String>
//}
