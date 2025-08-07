package com.example.pos.controller;

import com.example.pos.dto.ApiResponse;
import com.example.pos.dto.request.ProfileUpdateRequest;
import com.example.pos.dto.request.UserRequest;
import com.example.pos.dto.response.ResPageable;
import com.example.pos.dto.response.UserResponse;
import com.example.pos.entity.User;
import com.example.pos.entity.enums.UserRole;
import com.example.pos.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Users API")
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    @Operation(summary = "O'z profilini kurish")
    public ResponseEntity<ApiResponse<UserResponse>> getProfile(
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(userService.getProfile(user));
    }

    @PostMapping("/sellers")
    @Operation(summary = "Sotuvchilarni qo'shish")
    public ResponseEntity<ApiResponse<String>> addSeller(
            @RequestBody UserRequest request,
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(userService.addSeller(request,user));
    }

    @PutMapping("/profile")
    @Operation(summary = "Profilni tahrirlash")
    public ResponseEntity<ApiResponse<?>> updateProfile(
            @AuthenticationPrincipal User user,
            @RequestBody ProfileUpdateRequest updateRequest
    ){
        return ResponseEntity.ok(userService.updateProfile(user,updateRequest));
    }

    @GetMapping("/sellers")
    @Operation(summary = "Sotuvchilarni qidirish")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getSellers(
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(userService.getSellers(user));
    }



    @GetMapping("/search")
    @Operation(summary = "Userlarni filter qilish uchun api")
    public ResponseEntity<ApiResponse<ResPageable>> searchUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam UserRole userRole,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ){
        return ResponseEntity.ok(userService.searchUser(name,phone,userRole,page,size));
    }
}
