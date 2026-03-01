package com.autowise.demo.mapper;

import com.autowise.demo.dto.OrderDto;
import com.autowise.demo.dto.OrderGoldDto;
import com.autowise.demo.dto.OrderItemDto;
import com.autowise.demo.dto.OrderJewelleryDto;
import com.autowise.demo.model.Order;
import com.autowise.demo.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderDto toDto(Order o) {
        OrderDto d = new OrderDto();
        d.id = o.getId();
        d.customerName = o.getCustomerName();
        d.customerPhone = o.getCustomerPhone();
        d.status = String.valueOf(o.getStatus());
        d.totalPrice = o.getTotalPrice();
        d.createdAt = String.valueOf(o.getCreatedAt());

        d.items = o.getItems()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return d;
    }

    public OrderItemDto toDto(OrderItem i) {
        OrderItemDto d = new OrderItemDto();
        d.id = i.getId();
        d.type = String.valueOf(i.getType());
        d.productId = i.getProductId();
        d.productName = i.getProductName();
        d.unitPrice = i.getUnitPrice();
        d.qty = i.getQty();
        d.lineTotal = i.getLineTotal();
        d.customNote = i.getCustomNote();

        d.goldRows = i.getGoldRows().stream().map(g -> {
            OrderGoldDto x = new OrderGoldDto();
            x.goldSourceId = g.getGoldSourceId();
            x.craftId = g.getCraftId();
            x.weightPerUnit = g.getWeightPerUnit();
            return x;
        }).toList();

        d.jewelleryRows = i.getJewelleryRows().stream().map(j -> {
            OrderJewelleryDto x = new OrderJewelleryDto();
            x.gemsPackageId = j.getGemsPackageId();
            x.qtyPerUnit = j.getQtyPerUnit();
            return x;
        }).toList();

        return d;
    }
}