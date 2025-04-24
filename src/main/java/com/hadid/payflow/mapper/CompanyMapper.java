package com.hadid.payflow.mapper;

import com.hadid.payflow.dto.request.CompanyRequest;
import com.hadid.payflow.dto.response.CompanyResponse;
import com.hadid.payflow.entity.Company;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CompanyMapper {

    public Company toCompany(CompanyRequest request) {
        return Company.builder()
                .companyId(request.getCompanyId())
                .companyName(request.getCompanyName())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
    }

    public CompanyResponse toCompanyResponse(Company company) {
        return CompanyResponse.builder()
                .companyId(company.getCompanyId())
                .companyName(company.getCompanyName())
                .build();
    }

}
