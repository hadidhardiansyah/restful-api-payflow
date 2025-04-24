package com.hadid.payflow.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CompanyRequest {

    @NotEmpty(message = "Company ID is mandatory")
    @NotBlank(message = "Company ID is mandatory")
    private String companyId;

    @NotEmpty(message = "Company Name is mandatory")
    @NotBlank(message = "Company Name is mandatory")
    private String companyName;

}
