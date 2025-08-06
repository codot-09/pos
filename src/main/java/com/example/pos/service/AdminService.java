package com.example.pos.service;

import com.example.pos.dto.ApiResponse;
import com.example.pos.dto.request.UserRequest;
import com.example.pos.dto.response.UserResponse;
import com.example.pos.entity.User;
import com.example.pos.entity.enums.UserRole;
import com.example.pos.mapper.UserMapper;
import com.example.pos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final SubscriptionService subscriptionService;
    private final UserMapper mapper;

    public ApiResponse<String> addClient(UserRequest request, User admin,int subscriptionDays){
        if (userRepository.existsByPhone(request.getPhone())){
            return ApiResponse.error("Mijoz mavjud");
        }

        User user = User.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .passwordHash(encoder.encode(request.getPassword()))
                .role(UserRole.MARKET_OWNER)
                .active(true)
                .build();

        userRepository.save(user);

        subscriptionService.createSubscription(user,subscriptionDays);

        return ApiResponse.success("Mijoz qo'shildi");
    }

    public ApiResponse<List<UserResponse>> getUsers(){
        List<User> users = userRepository.findAll();

        return ApiResponse.success(mapper.toResponseList(users));
    }
}
