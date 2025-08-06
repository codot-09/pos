package com.example.pos.controller;

import com.example.pos.dto.ApiResponse;
import com.example.pos.dto.request.DebtRequest;
import com.example.pos.dto.response.DebtResponse;
import com.example.pos.entity.User;
import com.example.pos.service.DebtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/debts")
@RequiredArgsConstructor
@Tag(name = "Debts",description = "Qarzlar bilan ishlash")
public class DebtController {
    private final DebtService debtService;

    @GetMapping("/market")
    @Operation(summary = "Marketdagi qarzlarni ko'rish")
    public ApiResponse<Map<LocalDateTime, BigDecimal>> getDebtsByMarket(@AuthenticationPrincipal User user) {
        return debtService.getDebtsByMarket(user);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Id bo'yicha qarzni ko'rish")
    public ApiResponse<DebtResponse> getDebtById(@PathVariable UUID id) {
        return debtService.getById(id);
    }

    @PutMapping("/{id}/verify")
    @Operation(summary = "Qarzni tasdiqlash")
    public ApiResponse<String> verifyDebt(
            @RequestBody DebtRequest request,
            @PathVariable UUID id
    ) {
        return debtService.verifyDebt(request, id);
    }
}
