package com.example.pos.service;

import com.example.pos.dto.ApiResponse;
import com.example.pos.dto.UserDTO;
import com.example.pos.dto.request.ProfileUpdateRequest;
import com.example.pos.dto.request.UserRequest;
import com.example.pos.dto.response.LoginResponse;
import com.example.pos.dto.response.ResPageable;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MarketRepository marketRepository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;
    private final UserMapper userMapper;

    public ApiResponse<UserResponse> getProfile(User user){
        return ApiResponse.success(mapper.toResponse(user));
    }

    public ApiResponse<String> addSeller(UserRequest request, User owner){
        if (userRepository.existsByPhone(request.getPhone())){
            return ApiResponse.error("Sotuvchi mavjud");
        }

        Market market = marketRepository.findByOwnerId(owner.getId())
                .orElseThrow(() -> new DataNotFoundException("Market topilmadi"));

        if (!market.getOwner().isActive()){
            return ApiResponse.error("Obuna bo'lish talab qilinadi");
        }

        User user = User.builder()
                .phone(request.getPhone())
                .name(request.getName())
                .passwordHash(encoder.encode(request.getPassword()))
                .role(UserRole.SELLER)
                .market(market)
                .build();

        userRepository.save(user);

        return ApiResponse.success("Sotuvchi qo'shildi");
    }

    public ApiResponse<List<UserResponse>> getSellers(User owner){
        Market market = marketRepository.findByOwnerId(owner.getId())
                .orElseThrow(() -> new DataNotFoundException("Market topilmadi"));

        List<User> sellers = userRepository.findByRoleAndNameOrPhone(market.getId());

        return ApiResponse.success(mapper.toResponseList(sellers));
    }

    public ApiResponse<?> updateProfile(User user, ProfileUpdateRequest request) {

        if (request.getPhone() != null &&
                !request.getPhone().isBlank() &&
                !user.getPhone().equals(request.getPhone()) &&
                userRepository.existsByPhone(request.getPhone())) {
            return ApiResponse.error("Bu telefon raqami bilan boshqa foydalanuvchi mavjud");
        }

        if (request.getName() != null && !request.getName().isBlank()) {
            user.setName(request.getName());
        }

        if (request.getPhone() != null && !request.getPhone().isBlank()) {
            user.setPhone(request.getPhone());
        }

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPasswordHash(encoder.encode(request.getPassword()));
        }

        if (request.getImageUrl() != null && !request.getImageUrl().isBlank()) {
            user.setImageUrl(request.getImageUrl());
        }

        userRepository.save(user);

        if (request.getPhone() != null && !request.getPhone().isBlank() &&
                !request.getPhone().equals(user.getPhone())) {
            String token = jwtProvider.generateToken(user.getPhone());
            LoginResponse response = new LoginResponse(token, "Bearer", user.getRole().name());
            return ApiResponse.success(response);
        }

        return ApiResponse.success("Profil tahrirlandi");
    }



    public ApiResponse<ResPageable> searchUser(String name, String phoneNumber, UserRole userRole, int page, int size){
        Page<User> users = userRepository.searchUsers(name, phoneNumber, userRole.name(), PageRequest.of(page, size));
        if (users.getTotalElements() == 0) {
            return ApiResponse.success("User topilmadi");
        }

        List<UserDTO> userDTOList = users.stream().map(userMapper::toUserDTO).toList();
        ResPageable resPageable = ResPageable.builder()
                .page(page)
                .size(size)
                .totalElements(users.getTotalElements())
                .totalPage(users.getTotalPages())
                .body(userDTOList)
                .build();

        return ApiResponse.success(resPageable);
    }
}
