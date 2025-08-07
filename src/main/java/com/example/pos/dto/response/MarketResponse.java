package com.example.pos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarketResponse {
    private UUID id;
    private String name;
    private String address;
    private String imageUrl;
    private boolean status;
    private UUID ownerId;
    private String ownerName;
}
