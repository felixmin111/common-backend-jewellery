package com.autowise.demo.mapper;

import com.autowise.demo.dto.InvoiceResponseDto;
import com.autowise.demo.model.Invoice;
import com.autowise.demo.model.PurchaseItem;

import java.util.List;
import java.util.stream.Collectors;

public class InvoiceMapper {

    public static InvoiceResponseDto toDto(Invoice inv, List<PurchaseItem> items) {
        InvoiceResponseDto dto = new InvoiceResponseDto();

        dto.setId(inv.getId());
        dto.setInvoiceNo(inv.getInvoiceNo());
        dto.setCustomerId(inv.getCustomerId());
        dto.setSubTotal(inv.getSubTotal());
        dto.setDiscountAmount(inv.getDiscountAmount());
        dto.setDiscountPercentage(inv.getDiscountPercentage());
        dto.setFinalPrice(inv.getFinalPrice());
        dto.setStatus(inv.getStatus());
        dto.setCreatedAt(inv.getCreatedAt());
        dto.setUpdatedAt(inv.getUpdatedAt());

        var itemDtos = items.stream().map(it -> {
            InvoiceResponseDto.ItemDto i = new InvoiceResponseDto.ItemDto();
            i.setId(it.getId());
            i.setProductId(it.getProductId());
            i.setQty(it.getQty());
            i.setSellingPrice(it.getSellingPrice());
            i.setSubtotal(it.getSubtotal());
            i.setDiscountAmount(it.getDiscountAmount());
            i.setFinalPrice(it.getFinalPrice());
            return i;
        }).collect(Collectors.toList());

        dto.setItems(itemDtos);

        return dto;
    }
}