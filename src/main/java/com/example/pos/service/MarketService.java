package com.example.pos.service;

import com.example.pos.dto.ApiResponse;
import com.example.pos.dto.request.MarketCreateRequest;
import com.example.pos.dto.response.MarketResponse;
import com.example.pos.entity.Market;
import com.example.pos.entity.User;
import com.example.pos.exception.DataNotFoundException;
import com.example.pos.mapper.MarketMapper;
import com.example.pos.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MarketService {
    private final MarketRepository marketRepository;
    private final MarketMapper mapper;

    public ApiResponse<String> createMarket(MarketCreateRequest request, User owner){
        if (!owner.isActive()) {
            return ApiResponse.error("Obuna bo'lish talab qilinadi");
        }

        Market market = Market.builder()
                .name(request.getName())
                .address(request.getAddress())
                .imageUrl(request.getImageUrl())
                .owner(owner)
                .build();

        marketRepository.save(market);

        return ApiResponse.success("Market qo'shildi tasdiqlanishi kutilmoqda");
    }

    public ApiResponse<MarketResponse> findByOwner(User owner){
        Market market = marketRepository.findByOwnerId(owner.getId())
                .orElseThrow(() -> new DataNotFoundException("Market topilmadi"));

        return ApiResponse.success(mapper.toResponse(market));
    }

    public ApiResponse<MarketResponse> getById(UUID marketId){
        Market market = marketRepository.findById(marketId)
                .orElseThrow(() -> new DataNotFoundException("Market topilmadi"));

        return ApiResponse.success(mapper.toResponse(market));
    }

    public ApiResponse<List<MarketResponse>> getAll(){
        return ApiResponse.success(mapper.toResponseList(marketRepository.findAll()));
    }
}
