package com.hadid.payflow.mapper;

import com.hadid.payflow.dto.response.CompanyResponse;
import com.hadid.payflow.dto.response.InvoiceResponse;
import com.hadid.payflow.entity.Invoice;
import org.springframework.stereotype.Service;

@Service
public class InvoiceMapper {

    public InvoiceResponse toInvoiceResponse(Invoice invoice) {

        CompanyResponse fromCompanyResponse = CompanyResponse.builder()
                .companyId(invoice.getFromCompany().getCompanyId())
                .companyName(invoice.getFromCompany().getCompanyName())
                .build();

        CompanyResponse toCompanyResponse = CompanyResponse.builder()
                .companyId(invoice.getToCompany().getCompanyId())
                .companyName(invoice.getToCompany().getCompanyName())
                .build();

        return InvoiceResponse.builder()
                .invoiceNo(invoice.getInvoiceNo())
                .fromCompany(fromCompanyResponse)
                .toCompany(toCompanyResponse)
                .createdDate(invoice.getCreatedDate())
                .maturityDate(invoice.getMaturityDate())
                .status(invoice.getStatus().name())
                .amount(invoice.getAmount())
                .notes(invoice.getNotes())
                .build();
    }

}
