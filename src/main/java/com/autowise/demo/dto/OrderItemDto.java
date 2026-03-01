package com.autowise.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderItemDto {
    public Long id;
    public String type; // PRODUCT | CUSTOM

    public Long productId;
    public String productName;
    public Double unitPrice;
    public Integer qty;
    public Double lineTotal;

    public String customNote;

    public List<OrderGoldDto> goldRows;
    public List<OrderJewelleryDto> jewelleryRows;
}