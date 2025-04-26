package com.hadid.payflow.dto.response;

import com.hadid.payflow.entity.Company;
import com.hadid.payflow.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class InvoiceResponse {

    private String invoiceNo;

    private CompanyResponse fromCompany;

    private CompanyResponse toCompany;

    private LocalDateTime createdDate;

    private LocalDateTime maturityDate;

    private String status;

    private Double amount;

    private String notes;

}
