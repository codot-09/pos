package com.example.pos.service;

import com.example.pos.dto.ApiResponse;
import com.example.pos.dto.response.SubscriptionResponse;
import com.example.pos.entity.Subscription;
import com.example.pos.entity.User;
import com.example.pos.exception.DataNotFoundException;
import com.example.pos.mapper.SubscriptionMapper;
import com.example.pos.repository.SubscriptionRepository;
import com.example.pos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper mapper;
    private final UserRepository userRepository;

    public void createSubscription(User user,int expireDays){
        Subscription subscription = Subscription.builder()
                .user(user)
                .startDate(LocalDate.now())
                .expireDate(LocalDate.now().plusDays(expireDays))
                .price(new BigDecimal(10000))
                .build();

        subscriptionRepository.save(subscription);
    }

    @Scheduled(cron = "0 0 2 * * *")
    void remindSubscription(){
        List<Subscription> expiredSubscriptions = subscriptionRepository.findExpiredSubscriptions(LocalDate.now());

        List<User> usersToDeactivate = expiredSubscriptions.stream()
                .map(Subscription::getUser)
                .filter(User::isActive)
                .distinct()
                .peek(user -> user.setActive(false))
                .toList();

        userRepository.saveAll(usersToDeactivate);
    }

    public ApiResponse<String> buySubscription(UUID userId, int expireDays){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("Foydalanuvchi topilmadi"));

        Subscription subscription = subscriptionRepository.findByUserId(userId)
                .orElseThrow(() -> new DataNotFoundException("Obuna topilmadi"));

        subscription.setStartDate(LocalDate.now());
        subscription.setExpireDate(LocalDate.now().plusDays(expireDays));

        subscriptionRepository.save(subscription);

        return ApiResponse.success("Obuna faollashtirildi");
    }

    public ApiResponse<List<SubscriptionResponse>> getSubscriptions(){
        return ApiResponse.success(mapper.toResponseList(subscriptionRepository.findAll()));
    }
}
