package com.hadid.payflow.mapper;

import com.hadid.payflow.dto.request.InvoiceRequest;
import com.hadid.payflow.dto.response.CompanyResponse;
import com.hadid.payflow.dto.response.InvoiceResponse;
import com.hadid.payflow.entity.Company;
import com.hadid.payflow.entity.Invoice;
import com.hadid.payflow.entity.User;
import com.hadid.payflow.util.DateUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.hadid.payflow.enums.InvoiceStatus.PENDING_APPROVAL;

@Service
public class InvoiceMapper {

    public Invoice toInvoice(InvoiceRequest request, User user, Company company) {
        return Invoice.builder()
                .invoiceNo(request.getInvoiceNo())
                .fromCompany(user.getCompanies().get(0))
                .toCompany(company)
                .createdDate(LocalDateTime.now())
                .maturityDate(DateUtil.convertToLocalDate(request.getMaturityDate()))
                .status(PENDING_APPROVAL)
                .amount(request.getAmount())
                .notes(request.getNotes())
                .createdBy(user)
                .build();
    }

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
