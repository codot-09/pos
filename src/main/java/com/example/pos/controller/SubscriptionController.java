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

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
@Tag(name = "Subscription", description = "Obuna xizmatlari")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/buy")
    @Operation(summary = "Obuna sotib olish (foydalanuvchi tomonidan)")
    public ResponseEntity<ApiResponse<String>> buySubscription(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "30") int days // default 30 kunlik
    ) {
        return ResponseEntity.ok(subscriptionService.buySubscription(user.getId(), days));
    }

    @GetMapping
    @Operation(summary = "Barcha obunalarni olish (admin)")
    public ResponseEntity<ApiResponse<List<SubscriptionResponse>>> getAllSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getSubscriptions());
    }
}
