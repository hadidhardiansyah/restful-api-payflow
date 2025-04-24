package com.hadid.payflow.mapper;

import com.hadid.payflow.dto.response.ApiResponse;
import org.springframework.stereotype.Service;

@Service
public class ApiResponseMapper {

    public static <T> ApiResponse<T> successResponse(String message, T data) {
        return ApiResponse.<T>builder()
                .status("success")
                .message(message)
                .data(data)
                .build();
    }

    public static ApiResponse<?> errorResponse(String message, Object error) {
        return ApiResponse.builder()
                .status("error")
                .message(message)
                .error(error)
                .build();
    }

}
