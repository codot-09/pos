package com.example.pos.service;

import com.example.pos.dto.ApiResponse;
import com.example.pos.dto.request.MarketCreateRequest;
import com.example.pos.dto.response.MarketResponse;
import com.example.pos.entity.Market;
import com.example.pos.entity.User;
import com.example.pos.mapper.MarketMapper;
import com.example.pos.repository.MarketRepository;
import com.example.pos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketService {
    private final MarketRepository marketRepository;
    private final MarketMapper mapper;

    public ApiResponse<String> createMarket(MarketCreateRequest request, User owner){
        Market market = Market.builder()
                .name(request.getName())
                .address(request.getAddress())
                .imageUrl(request.getImageUrl())
                .owner(owner)
                .build();

        marketRepository.save(market);

        return ApiResponse.success("Market qo'shildi tasdiqlanishi kutilmoqda");
    }

    public ApiResponse<List<MarketResponse>> findByOwner(User owner){
        List<Market> markets = marketRepository.findByOwnerId(owner.getId());

        return ApiResponse.success(mapper.toResponseList(markets));
    }
}
