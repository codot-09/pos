package com.example.pos.service;

import com.example.pos.dto.ApiResponse;
import com.example.pos.dto.request.AuthRequest;
import com.example.pos.dto.response.LoginResponse;
import com.example.pos.entity.User;
import com.example.pos.exception.DataNotFoundException;
import com.example.pos.repository.UserRepository;
import com.example.pos.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public ApiResponse<LoginResponse> login(AuthRequest request) {
        User user = userRepository.findByPhone(request.getPhone())
                .orElseThrow(() -> new DataNotFoundException("Foydalanuvchi topilmadi"));

        String token = jwtProvider.generateToken(user.getPhone());

        LoginResponse response = new LoginResponse(token,"Bearer",user.getRole().name());

        return ApiResponse.success(response);
    }
}
