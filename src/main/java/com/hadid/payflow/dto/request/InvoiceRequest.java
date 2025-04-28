package com.hadid.payflow.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InvoiceRequest {

    @NotEmpty(message = "Invoice Number is mandatory")
    @NotBlank(message = "Invoice Number is mandatory")
    private String invoiceNo;

    @NotEmpty(message = "Company ID is mandatory")
    @NotBlank(message = "Company ID is mandatory")
    private String companyId;

    @NotEmpty(message = "Maturity Date is mandatory")
    @NotBlank(message = "Maturity Date is mandatory")
    private String maturityDate;

    @NotNull(message = "Amount cannot be null")
    @Min(value = 0, message = "Amount must be greater than 0")
    private Double amount;

    private String notes;

}
