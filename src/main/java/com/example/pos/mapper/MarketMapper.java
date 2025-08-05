package com.example.pos.mapper;

import com.example.pos.dto.response.MarketResponse;
import com.example.pos.entity.Market;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MarketMapper {
    public MarketResponse toResponse(Market market){
        return new MarketResponse(
                market.getId(),
                market.getName(),
                market.getAddress(),
                market.getImageUrl(),
                market.isVerified(),
                market.getOwner().getId()
        );
    }

    public List<MarketResponse> toResponseList(List<Market> markets){
        return markets.stream()
                .map(this::toResponse)
                .toList();
    }
}
