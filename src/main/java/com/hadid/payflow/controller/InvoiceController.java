package com.hadid.payflow.controller;

import com.hadid.payflow.dto.response.ApiResponse;
import com.hadid.payflow.dto.response.InvoiceResponse;
import com.hadid.payflow.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<InvoiceResponse>>> getAllInvoicesByUserId(Authentication connectedUser) {
        ApiResponse<List<InvoiceResponse>> response = invoiceService.getAllInvoices(connectedUser);
        return ResponseEntity.ok(response);
    }

}
