package com.hadid.payflow.service;

import com.hadid.payflow.dto.request.CompanyRequest;
import com.hadid.payflow.dto.response.ApiResponse;
import com.hadid.payflow.dto.response.CompanyResponse;
import com.hadid.payflow.entity.Company;
import com.hadid.payflow.exception.BusinessException;
import com.hadid.payflow.mapper.ApiResponseMapper;
import com.hadid.payflow.mapper.CompanyMapper;
import com.hadid.payflow.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.hadid.payflow.exception.BusinessErrorCodes.COMPANY_ID_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    public ApiResponse<CompanyResponse> createCompany(CompanyRequest request) {

        if (companyRepository.existsByCompanyId(request.getCompanyId())) {
            throw new BusinessException(COMPANY_ID_ALREADY_EXISTS);
        }

        Company newCompany = companyMapper.toCompany(request);

        companyRepository.save(newCompany);

        CompanyResponse companyResponse = companyMapper.toCompanyResponse(newCompany);

        return ApiResponseMapper.successResponse("Company created successfully", companyResponse);

    }

}
