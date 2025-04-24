package com.hadid.payflow.exception;

import com.hadid.payflow.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<ExceptionResponse>> handleBusinessException(BusinessException exception) {
        BusinessErrorCodes errorCode = exception.getErrorCode();

        ExceptionResponse errorDetails = ExceptionResponse.builder()
                .businessErrorCode(errorCode.getCode())
                .businessErrorDescription(errorCode.getDescription())
                .build();

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(
                        ApiResponse.<ExceptionResponse>builder()
                                .status("error")
                                .message("Business error occurred")
                                .error(errorDetails)
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        ApiResponse<Map<String, String>> response = ApiResponse.<Map<String, String>>builder()
                .status("error")
                .message("Validation error occurred")
                .error(errors)
                .build();

        return ResponseEntity.status(BAD_REQUEST).body(response);
    }

}
