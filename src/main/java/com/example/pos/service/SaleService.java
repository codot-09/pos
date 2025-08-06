package com.example.pos.service;

import com.example.pos.dto.ApiResponse;
import com.example.pos.dto.request.SaleRequest;
import com.example.pos.dto.response.SalesResponse;
import com.example.pos.entity.*;
import com.example.pos.exception.DataNotFoundException;
import com.example.pos.mapper.SalesMapper;
import com.example.pos.repository.DebtRepository;
import com.example.pos.repository.MarketRepository;
import com.example.pos.repository.ProductRepository;
import com.example.pos.repository.SaleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final DebtRepository debtRepository;
    private final MarketRepository marketRepository;
    private final SalesMapper mapper;

    @Transactional
    public ApiResponse<String> sale(List<SaleRequest> requests, User seller, BigDecimal paidAmount) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (SaleRequest request : requests) {
            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new DataNotFoundException("Mahsulot topilmadi"));

            BigDecimal requestedQty = request.getPrice().divide(product.getSalesPrice(), 2, BigDecimal.ROUND_HALF_UP);
            if (BigDecimal.valueOf(product.getQuantity()).compareTo(requestedQty) < 0) {
                return ApiResponse.error("Mahsulot yetarli emas: " + product.getName());
            }

            product.setQuantity(product.getQuantity() - requestedQty.doubleValue());
            productRepository.save(product);

            totalAmount = totalAmount.add(request.getPrice());
        }

        Market market = marketRepository.findMarketBySeller(seller.getId())
                .orElseThrow(() -> new DataNotFoundException("Market topilmadi"));

        Sales sales = Sales.builder()
                .salesPerson(seller)
                .totalPrice(totalAmount)
                .payedPrice(paidAmount)
                .paid(paidAmount.compareTo(totalAmount) >= 0)
                .market(market)
                .build();

        saleRepository.save(sales);

        if (paidAmount.compareTo(totalAmount) < 0) {
            BigDecimal debtAmount = totalAmount.subtract(paidAmount);
            Debt debt = debtRepository.save(
                    Debt.builder()
                            .sales(sales)
                            .debtAmount(debtAmount)
                            .build());

            return ApiResponse.success("Qarzga yozildi. Debt ID: " + debt.getId());
        }

        return ApiResponse.success("Sotuv muvaffaqiyatli yaratildi");
    }

    public ApiResponse<Map<LocalDate, BigDecimal>> getHistoryByMarket(UUID marketId) {
        Market market = marketRepository.findById(marketId)
                .orElseThrow(() -> new DataNotFoundException("Market topilmadi"));

        List<Sales> sales = saleRepository.findByMarketId(marketId);

        Map<LocalDate, BigDecimal> response = sales.stream()
                .collect(
                        Collectors.groupingBy(
                                sale -> sale.getSalesDate().toLocalDate(),
                                Collectors.reducing(
                                        BigDecimal.ZERO,
                                        Sales::getTotalPrice,
                                        BigDecimal::add
                                )
                        )
                );

        return ApiResponse.success(response);
    }

    public ApiResponse<SalesResponse> getById(UUID saleId){
        Sales sales = saleRepository.findById(saleId)
                .orElseThrow(() -> new DataNotFoundException("Sotuv topilmadi"));

        return ApiResponse.success(mapper.toResponse(sales));
    }
}
