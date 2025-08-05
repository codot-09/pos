package com.example.pos.mapper;

import com.example.pos.dto.response.UserResponse;
import com.example.pos.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public UserResponse toResponse(User user){
        return new UserResponse(
                user.getName(),
                user.getPhone(),
                user.getRole().name(),
                user.getImageUrl()
        );
    }

    public List<UserResponse> toResponseList(List<User> users){
        return users.stream()
                .map(this::toResponse)
                .toList();
    }
}
