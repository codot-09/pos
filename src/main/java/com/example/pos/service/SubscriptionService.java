package com.example.pos.service;

import com.example.pos.entity.User;
import com.example.pos.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public void createSubscription(User user){

    }
}
