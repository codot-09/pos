package com.example.pos.controller;

import com.example.pos.dto.ApiResponse;
import com.example.pos.dto.response.SubscriptionResponse;
import com.example.pos.entity.User;
import com.example.pos.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
@Tag(name = "Subscription", description = "Obuna xizmatlari")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/buy/{userId}")
    @Operation(summary = "Obuna sotib olish (foydalanuvchi tomonidan)")
    public ResponseEntity<ApiResponse<String>> buySubscription(
            @RequestParam(defaultValue = "30") int days ,
            @PathVariable UUID userId
    ) {
        return ResponseEntity.ok(subscriptionService.buySubscription(userId, days));
    }

    @GetMapping
    @Operation(summary = "Barcha obunalarni olish (admin)")
    public ResponseEntity<ApiResponse<List<SubscriptionResponse>>> getAllSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getSubscriptions());
    }
}
