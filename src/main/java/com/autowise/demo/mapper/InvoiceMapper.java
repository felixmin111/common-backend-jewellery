package com.autowise.demo.mapper;

import com.autowise.demo.dto.InvoiceResponseDto;
import com.autowise.demo.model.Invoice;
import com.autowise.demo.model.PurchaseItem;

import java.util.List;
import java.util.stream.Collectors;

public class InvoiceMapper {

    public static InvoiceResponseDto toDto(Invoice inv, List<PurchaseItem> items) {
        return InvoiceResponseDto.builder()
                .id(inv.getId())
                .invoiceNo(inv.getInvoiceNo())
                .customerId(inv.getCustomerId())
                .subTotal(inv.getSubTotal())
                .discountAmount(inv.getDiscountAmount())
                .discountPercentage(inv.getDiscountPercentage())
                .finalPrice(inv.getFinalPrice())
                .status(inv.getStatus())
                .createdAt(inv.getCreatedAt())
                .updatedAt(inv.getUpdatedAt())
                .items(items.stream().map(it ->
                        InvoiceResponseDto.ItemDto.builder()
                                .id(it.getId())
                                .productId(it.getProductId())
                                .qty(it.getQty())
                                .sellingPrice(it.getSellingPrice())
                                .subtotal(it.getSubtotal())
                                .discountAmount(it.getDiscountAmount())
                                .finalPrice(it.getFinalPrice())
                                .build()
                ).collect(Collectors.toList()))
                .build();
    }
}