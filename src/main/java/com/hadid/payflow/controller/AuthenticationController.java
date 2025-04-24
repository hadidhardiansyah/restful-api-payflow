package com.hadid.payflow.controller;

import com.hadid.payflow.dto.request.UserRegistrationRequest;
import com.hadid.payflow.dto.response.ApiResponse;
import com.hadid.payflow.entity.User;
import com.hadid.payflow.service.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;


@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody @Valid UserRegistrationRequest request) throws MessagingException {
        ApiResponse<User> response = authenticationService.register(request);
        return ResponseEntity.status(CREATED).body(response);
    }

}
