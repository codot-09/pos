package com.example.pos.controller;

import com.example.pos.dto.ApiResponse;
import com.example.pos.dto.request.SaleRequest;
import com.example.pos.dto.response.SalesResponse;
import com.example.pos.entity.User;
import com.example.pos.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
@Tag(name = "Sales", description = "Sotuvlar bilan ishlash")
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    @Operation(summary = "Yangi sotuv qo‘shish")
    public ResponseEntity<ApiResponse<String>> sale(
            @RequestBody List<SaleRequest> requests,
            @RequestParam BigDecimal paidAmount,
            @AuthenticationPrincipal User seller
    ) {
        return ResponseEntity.ok(saleService.sale(requests, seller, paidAmount));
    }

    @GetMapping("/history/{marketId}")
    @Operation(summary = "Market bo‘yicha kunlik sotuvlar statistikasi")
    public ResponseEntity<ApiResponse<Map<LocalDate, BigDecimal>>> getHistoryByMarket(
            @PathVariable UUID marketId
    ) {
        return ResponseEntity.ok(saleService.getHistoryByMarket(marketId));
    }

    @GetMapping("/{saleId}")
    @Operation(summary = "Id bo'yicha satuvni ko'rish")
    public ResponseEntity<ApiResponse<SalesResponse>> getById(@PathVariable UUID saleId){
        return ResponseEntity.ok(saleService.getById(saleId));
    }
}
