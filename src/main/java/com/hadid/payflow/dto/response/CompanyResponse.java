package com.hadid.payflow.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CompanyResponse {

    private String companyId;

    private String companyName;

}
