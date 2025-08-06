package com.example.pos.controller;

import com.example.pos.dto.ApiResponse;
import com.example.pos.dto.request.MarketCreateRequest;
import com.example.pos.dto.response.MarketResponse;
import com.example.pos.entity.User;
import com.example.pos.service.MarketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/markets")
@RequiredArgsConstructor
@Tag(name = "Market",description = "Marketlar bilan ishlash")
public class MarketController {
    private final MarketService marketService;

    @PostMapping
    @Operation(summary = "Market qo'shish")
    public ResponseEntity<ApiResponse<String>> addMarket(
            @RequestBody MarketCreateRequest request,
            @AuthenticationPrincipal User owner
    ){
        return ResponseEntity.ok(marketService.createMarket(request, owner));
    }

    @GetMapping("/my")
    @Operation(summary = "O'z marketini ko'rish")
    public ResponseEntity<ApiResponse<MarketResponse>> getMarket(
            @AuthenticationPrincipal User owner
    ){
        return ResponseEntity.ok(marketService.findByOwner(owner));
    }

    @GetMapping("/all")
    @Operation(summary = "Barcha marketlarni ko'rish")
    public ResponseEntity<ApiResponse<List<MarketResponse>>> getMarkets(){
        return ResponseEntity.ok(marketService.getAll());
    }

    @GetMapping("/{marketId}")
    @Operation(summary = "ID bo'yicha marketni ko'rish")
    public ResponseEntity<ApiResponse<MarketResponse>> getById(
            @PathVariable UUID marketId
    ){
        return ResponseEntity.ok(marketService.getById(marketId));
    }
}
