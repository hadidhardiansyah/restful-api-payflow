package com.hadid.payflow.controller;

import com.hadid.payflow.dto.request.CompanyRequest;
import com.hadid.payflow.dto.response.ApiResponse;
import com.hadid.payflow.dto.response.CompanyResponse;
import com.hadid.payflow.service.CompanyService;
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
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CompanyResponse>> create(@RequestBody @Valid CompanyRequest request) {
        ApiResponse<CompanyResponse> response = companyService.createCompany(request);
        return ResponseEntity.status(CREATED).body(response);
    }

}
