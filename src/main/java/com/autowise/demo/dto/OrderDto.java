package com.autowise.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDto {
    public Long id;
    public String customerName;
    public String customerPhone;
    public String status;      // OrderStatus
    public Double totalPrice;
    public List<OrderItemDto> items;
    public String createdAt;
}
