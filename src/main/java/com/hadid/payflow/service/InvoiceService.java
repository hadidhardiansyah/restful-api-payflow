package com.hadid.payflow.service;

import com.hadid.payflow.dto.request.InvoiceRequest;
import com.hadid.payflow.dto.response.ApiResponse;
import com.hadid.payflow.dto.response.InvoiceResponse;
import com.hadid.payflow.entity.Company;
import com.hadid.payflow.entity.Invoice;
import com.hadid.payflow.entity.User;
import com.hadid.payflow.entity.UserPrincipal;
import com.hadid.payflow.exception.BusinessException;
import com.hadid.payflow.mapper.ApiResponseMapper;
import com.hadid.payflow.mapper.InvoiceMapper;
import com.hadid.payflow.repository.CompanyRepository;
import com.hadid.payflow.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.hadid.payflow.exception.BusinessErrorCodes.COMPANY_ID_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final CompanyRepository companyRepository;

    private final InvoiceRepository invoiceRepository;

    private final InvoiceMapper invoiceMapper;

    public ApiResponse<List<InvoiceResponse>> getAllInvoices(Authentication connectedUser) {
        UserPrincipal userPrincipal = (UserPrincipal) connectedUser.getPrincipal();
        User user = userPrincipal.getUser();

        List<Invoice> invoices = invoiceRepository.findAllByCreatedBy(user);

        List<InvoiceResponse> invoiceResponses = invoices.stream()
                .map(invoiceMapper::toInvoiceResponse)
                .toList();

        return ApiResponse.<List<InvoiceResponse>>builder()
                .data(invoiceResponses.isEmpty() ? List.of() : invoiceResponses)
                .build();
    }

    public ApiResponse<InvoiceResponse> createInvoice(InvoiceRequest request, Authentication connectedUser) {

        UserPrincipal userPrincipal = (UserPrincipal) connectedUser.getPrincipal();
        User user = userPrincipal.getUser();

        Company company = companyRepository.findByCompanyId(request.getCompanyId())
                .orElseThrow(() -> new BusinessException(COMPANY_ID_NOT_FOUND));

        Invoice newInvoice = invoiceMapper.toInvoice(request, user, company);

        invoiceRepository.save(newInvoice);

        InvoiceResponse invoiceResponse = invoiceMapper.toInvoiceResponse(newInvoice);

        return ApiResponseMapper.successResponse(
                "Invoice created successfully",
                invoiceResponse
        );

    }

}
