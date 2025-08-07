package com.example.pos.controller;

import com.example.pos.dto.ApiResponse;
import com.example.pos.dto.request.ProductRequest;
import com.example.pos.dto.response.ProductResponse;
import com.example.pos.dto.response.ResPageable;
import com.example.pos.entity.enums.UnitsOfMeasure;
import com.example.pos.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Product",description = "Mahsulotlar bilan ishlash")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Product qo'shish")
    public ResponseEntity<ApiResponse<String>> addProduct(@RequestBody ProductRequest request,
                                                          @RequestParam UUID categoryId,
                                                          @RequestParam UnitsOfMeasure measure) {
        return ResponseEntity.ok(productService.addProduct(request, categoryId, measure));
    }

    @GetMapping("/market/{marketId}")
    @Operation(summary = "Market bo'yicha productlarni ko'rish")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> findByMarket(@PathVariable UUID marketId) {
        return ResponseEntity.ok(productService.findByMarket(marketId));
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Id bo'yicha productni ko'rish")
    public ResponseEntity<ApiResponse<ProductResponse>> getById(@PathVariable UUID productId) {
        return ResponseEntity.ok(productService.getById(productId));
    }

    @PutMapping("/{productId}")
    @Operation(summary = "Productni tahrirlash")
    public ResponseEntity<ApiResponse<String>> updateProduct(@RequestBody ProductRequest request,
                                                             @PathVariable UUID productId,
                                                             @RequestParam UUID categoryId,
                                                             @RequestParam UnitsOfMeasure measure) {
        return ResponseEntity.ok(productService.updateProduct(request, productId, categoryId, measure));
    }



    @GetMapping("/search")
    @Operation(summary = "Product filtr api")
    public ResponseEntity<ApiResponse<ResPageable>> searchProduct(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String barcode,
            @RequestParam(required = false) UUID marketId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity.ok(productService.searchProduct(categoryName, name,marketId, barcode, page, size));
    }
}
