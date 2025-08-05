package com.example.pos.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketCreateRequest {
    private String name;
    private String address;
    private String imageUrl;
}
