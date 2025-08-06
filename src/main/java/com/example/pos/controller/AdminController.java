package com.example.pos.controller;

import com.example.pos.dto.ApiResponse;
import com.example.pos.dto.request.UserRequest;
import com.example.pos.dto.response.UserResponse;
import com.example.pos.entity.User;
import com.example.pos.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
@Tag(name = "Admin",description = "Admin panel")
public class AdminController {
    private final AdminService adminService;

    @PostMapping
    @Operation(summary = "Mijoz qo'shish")
    public ResponseEntity<ApiResponse<String>> addClient(
            @RequestBody UserRequest userRequest,
            @AuthenticationPrincipal User admin,
            @RequestParam int subscriptionDays
    ){
        return ResponseEntity.ok(adminService.addClient(userRequest, admin, subscriptionDays));
    }

    @GetMapping
    @Operation(summary = "Barcha userlarni ko'rish")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getUsers(){
        return ResponseEntity.ok(adminService.getUsers());
    }
}
