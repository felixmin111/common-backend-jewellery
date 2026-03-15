package com.autowise.demo.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorInvoiceLookupDto {

    private Long invoiceId;
    private String invoiceNo;
    private Long customerId;
    private String customerName;
    private String customerPhone;
    private List<VendorInvoiceItemDto> items;
}