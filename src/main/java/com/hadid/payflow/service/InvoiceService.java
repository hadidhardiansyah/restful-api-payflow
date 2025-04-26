package com.hadid.payflow.service;

import com.hadid.payflow.dto.response.ApiResponse;
import com.hadid.payflow.dto.response.InvoiceResponse;
import com.hadid.payflow.entity.Invoice;
import com.hadid.payflow.entity.User;
import com.hadid.payflow.entity.UserPrincipal;
import com.hadid.payflow.mapper.InvoiceMapper;
import com.hadid.payflow.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {

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

}
