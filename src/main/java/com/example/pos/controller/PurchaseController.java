package com.example.pos.controller;

import com.example.pos.dto.ApiResponse;
import com.example.pos.dto.request.ProductPurchaseRequest;
import com.example.pos.dto.response.PurchaseResponse;
import com.example.pos.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/purchases")
@RequiredArgsConstructor
@Tag(name = "Purchase", description = "Mahsulot kirimlari bilan ishlash")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping
    @Operation(summary = "Yangi mahsulot kirimini qo‘shish")
    public ResponseEntity<ApiResponse<String>> purchaseProduct(
            @RequestBody ProductPurchaseRequest request
    ) {
        return ResponseEntity.ok(purchaseService.purchaseProduct(request));
    }

    @GetMapping("/market/{marketId}")
    @Operation(summary = "Market bo‘yicha barcha mahsulot kirimlarini olish")
    public ResponseEntity<ApiResponse<List<PurchaseResponse>>> getByMarket(
            @PathVariable UUID marketId
    ) {
        return ResponseEntity.ok(purchaseService.getByMarket(marketId));
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "Mahsulot bo‘yicha kirimlar tarixini ko‘rish")
    public ResponseEntity<ApiResponse<List<PurchaseResponse>>> getByProduct(
            @PathVariable UUID productId
    ) {
        return ResponseEntity.ok(purchaseService.getByProduct(productId));
    }
}
