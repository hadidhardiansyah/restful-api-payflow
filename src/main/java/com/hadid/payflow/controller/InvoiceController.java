package com.hadid.payflow.controller;

import com.hadid.payflow.dto.request.InvoiceRequest;
import com.hadid.payflow.dto.response.ApiResponse;
import com.hadid.payflow.dto.response.InvoiceResponse;
import com.hadid.payflow.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

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

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<InvoiceResponse>> createInvoice(@RequestBody @Valid InvoiceRequest request, Authentication connectedUser) {
        ApiResponse<InvoiceResponse> response = invoiceService.createInvoice(request, connectedUser);
        return ResponseEntity.status(CREATED).body(response);
    }

}
