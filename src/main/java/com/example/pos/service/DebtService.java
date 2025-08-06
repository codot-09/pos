package com.example.pos.service;

import com.example.pos.dto.ApiResponse;
import com.example.pos.dto.request.DebtRequest;
import com.example.pos.dto.response.DebtResponse;
import com.example.pos.entity.Debt;
import com.example.pos.entity.Market;
import com.example.pos.entity.User;
import com.example.pos.exception.DataNotFoundException;
import com.example.pos.mapper.DebtMapper;
import com.example.pos.repository.DebtRepository;
import com.example.pos.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DebtService {
    private final DebtRepository debtRepository;
    private final MarketRepository marketRepository;
    private final DebtMapper mapper;

    public ApiResponse<String> verifyDebt(DebtRequest request, UUID id){
        Debt debt = debtRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Qarz yozuvi"));

        debt.setDebtor(request.getDebtorName());
        debt.setContact(request.getContactInfos());

        debtRepository.save(debt);

        return ApiResponse.success("Qarz yozuvi saqlandi");
    }

    public ApiResponse<Map<LocalDateTime, BigDecimal>> getDebtsByMarket(User user) {
        Market market = marketRepository.findByOwnerId(user.getId())
                .orElseThrow(() -> new DataNotFoundException("Market topilmadi"));

        List<Debt> debts = debtRepository.findByMarket(market.getId());

        Map<LocalDateTime, BigDecimal> debtMap = debts.stream()
                .collect(Collectors.groupingBy(
                        Debt::getCreatedAt,
                        Collectors.reducing(BigDecimal.ZERO, Debt::getDebtAmount, BigDecimal::add)
                ));

        return ApiResponse.success(debtMap);
    }

    public ApiResponse<DebtResponse> getById(UUID id){
        Debt debt = debtRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Qarz yozuvi topilmadi"));

        return ApiResponse.success(mapper.toResponse(debt));
    }
}
