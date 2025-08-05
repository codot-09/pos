package com.example.pos.service;

import com.example.pos.dto.ApiResponse;
import com.example.pos.dto.request.ProfileUpdateRequest;
import com.example.pos.dto.request.UserRequest;
import com.example.pos.dto.response.LoginResponse;
import com.example.pos.dto.response.UserResponse;
import com.example.pos.entity.Market;
import com.example.pos.entity.User;
import com.example.pos.entity.enums.UserRole;
import com.example.pos.exception.DataNotFoundException;
import com.example.pos.mapper.UserMapper;
import com.example.pos.repository.MarketRepository;
import com.example.pos.repository.UserRepository;
import com.example.pos.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MarketRepository marketRepository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;

    public ApiResponse<UserResponse> getProfile(User user){
        return ApiResponse.success(mapper.toResponse(user));
    }

    public ApiResponse<String> addSeller(UserRequest request, UUID marketId){
        if (userRepository.existsByPhone(request.getPhone())){
            return ApiResponse.error("Sotuvchi mavjud");
        }

        Market market = marketRepository.findById(marketId)
                .orElseThrow(() -> new DataNotFoundException("Market topilmadi"));

        User user = User.builder()
                .phone(request.getPhone())
                .name(request.getName())
                .passwordHash(encoder.encode(request.getPassword()))
                .role(UserRole.SELLER)
                .build();

        userRepository.save(user);

        return ApiResponse.success("Sotuvchi qo'shildi");
    }

    public ApiResponse<List<UserResponse>> getSellers(String field,UUID marketId){
        List<User> sellers = userRepository.findByRoleAndNameOrPhone(field,marketId);

        return ApiResponse.success(mapper.toResponseList(sellers));
    }

    public ApiResponse<?> updateProfile(User user, ProfileUpdateRequest request) {
        if (!user.getPhone().equals(request.getPhone()) &&
                userRepository.existsByPhone(request.getPhone())) {
            return ApiResponse.error("Foydalanuvchi mavjud");
        }

        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setPasswordHash(encoder.encode(request.getPassword()));
        user.setImageUrl(request.getImageUrl());

        userRepository.save(user);

        if (!user.getPhone().equals(request.getPhone())) {
            String token = jwtProvider.generateToken(user.getPhone());
            LoginResponse response = new LoginResponse(token, "Bearer", user.getRole().name());
            return ApiResponse.success(response);
        }

        return ApiResponse.success("Profil tahrirlandi");
    }
}
