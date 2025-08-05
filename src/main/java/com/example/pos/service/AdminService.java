package com.example.pos.service;

import com.example.pos.dto.ApiResponse;
import com.example.pos.dto.request.UserRequest;
import com.example.pos.entity.User;
import com.example.pos.entity.enums.UserRole;
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

    public ApiResponse<String> addClient(UserRequest request, User admin){
        if (userRepository.existsByPhone(request.getPhone())){
            return ApiResponse.error("Mijoz mavjud");
        }

        User user = User.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .passwordHash(encoder.encode(request.getPassword()))
                .role(UserRole.MARKET_OWNER)
                .build();

        userRepository.save(user);

        return ApiResponse.success("Mijoz qo'shildi");
    }

    public ApiResponse<List<>>
}
