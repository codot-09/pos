package com.example.pos.mapper;

import com.example.pos.dto.response.SubscriptionResponse;
import com.example.pos.entity.Subscription;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class SubscriptionMapper {
    public SubscriptionResponse toResponse(Subscription subscription){
        return new SubscriptionResponse(
                subscription.getId(),
                subscription.getUser().getId(),
                subscription.getStartDate(),
                subscription.getExpireDate()
        );
    }

    public List<SubscriptionResponse> toResponseList(List<Subscription> subscriptions){
        return subscriptions.stream()
                .map(this::toResponse)
                .toList();
    }
}
