package com.example.pos.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private String message;
    private T data;
    private boolean success;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(null, data, true);
    }


    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(message,null,true);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(message, data, true);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(message, null, false);
    }
}
