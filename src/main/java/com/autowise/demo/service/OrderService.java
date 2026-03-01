package com.autowise.demo.service;

import com.autowise.demo.dto.OrderDto;
import com.autowise.demo.dto.OrderGoldDto;
import com.autowise.demo.dto.OrderItemDto;
import com.autowise.demo.dto.OrderJewelleryDto;
import com.autowise.demo.mapper.OrderMapper;
import com.autowise.demo.model.*;
import com.autowise.demo.model.enums.OrderItemType;
import com.autowise.demo.model.enums.OrderStatus;
import com.autowise.demo.repository.GemsPackageRepository;
import com.autowise.demo.repository.GoldSourceRepository;
import com.autowise.demo.repository.OrderRepository;
import com.autowise.demo.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;
    private final GoldSourceRepository goldSourceRepo;
    private final GemsPackageRepository gemsPackageRepo;
    private final OrderMapper mapper;

    @Transactional
    public OrderDto create(OrderDto req, boolean allowBackorder) {
        Order order = Order.builder()
                .customerName(req.customerName)
                .customerPhone(req.customerPhone)
                .status(OrderStatus.PENDING)
                .build();

        double total = 0;

        for (OrderItemDto itemReq : req.items) {
            OrderItemType type = OrderItemType.valueOf(itemReq.type);

            OrderItem item = OrderItem.builder()
                    .order(order)
                    .type(type)
                    .qty(Math.max(0, itemReq.qty == null ? 0 : itemReq.qty))
                    .customNote(itemReq.customNote)
                    .build();

            if (item.getQty() <= 0) throw new RuntimeException("Item qty must be > 0");

            if (type == OrderItemType.PRODUCT) {
                if (itemReq.productId == null) throw new RuntimeException("productId is required");

                Product p = productRepo.findById(itemReq.productId)
                        .orElseThrow(() -> new RuntimeException("Product not found: " + itemReq.productId));

                int stock = (int) (p.getQty() == null ? 0 : p.getQty());
                if (stock < item.getQty()) {
                    if (!allowBackorder) {
                        throw new RuntimeException("Out of stock: " + p.getName() + " (have " + stock + ")");
                    }
                    order.setStatus(OrderStatus.BACKORDERED);
                    // do NOT deduct stock
                } else {
                    // deduct stock now
                    p.setQty((long) (stock - item.getQty()));
                    productRepo.save(p);
                }

                item.setProductId(p.getId());
                item.setProductName(p.getName());
                item.setUnitPrice(p.getFinalPrice() == null ? 0d : p.getFinalPrice().doubleValue());

                double line = item.getUnitPrice() * item.getQty();
                item.setLineTotal(line);
                total += line;
            }

            if (type == OrderItemType.CUSTOM) {
                // You can set a custom unitPrice from frontend or calculate later
                double unit = itemReq.unitPrice == null ? 0 : itemReq.unitPrice;
                item.setUnitPrice(unit);

                // build gold rows (and multiply by qty)
                if (itemReq.goldRows != null) {
                    for (OrderGoldDto g : itemReq.goldRows) {
                        if (g.goldSourceId == null || g.craftId == null) throw new RuntimeException("gold row invalid");
                        double per = g.weightPerUnit == null ? 0 : g.weightPerUnit;
                        if (per <= 0) throw new RuntimeException("weightPerUnit must be > 0");

                        double totalW = per * item.getQty();

                        // ✅ CHECK remaining weight
                        // IMPORTANT: this assumes GoldSource.weight is REMAINING weight
                        GoldSource gs = goldSourceRepo.findById(g.goldSourceId)
                                .orElseThrow(() -> new RuntimeException("GoldSource not found: " + g.goldSourceId));
                        double remain = gs.getWeight() == null ? 0 : gs.getWeight();

                        if (totalW > remain) {
                            throw new RuntimeException("GoldSource not enough. Need " + totalW + " but have " + remain);
                        }

                        // ✅ DEDUCT
                        gs.setWeight((float) (remain - totalW));
                        goldSourceRepo.save(gs);

                        item.getGoldRows().add(OrderGold.builder()
                                .orderItem(item)
                                .goldSourceId(g.goldSourceId)
                                .craftId(g.craftId)
                                .weightPerUnit(per)
                                .totalWeight(totalW)
                                .build());
                    }
                }

                // build jewellery rows (and multiply by qty)
                if (itemReq.jewelleryRows != null) {
                    for (OrderJewelleryDto j : itemReq.jewelleryRows) {
                        int per = j.qtyPerUnit == null ? 0 : j.qtyPerUnit;
                        if (j.gemsPackageId == null || per <= 0) throw new RuntimeException("jewellery row invalid");

                        int totalQty = per * item.getQty();

                        GemsPackage gp = gemsPackageRepo.findById(j.gemsPackageId)
                                .orElseThrow(() -> new RuntimeException("GemsPackage not found: " + j.gemsPackageId));

                        int remain = gp.getCurrentQuantity() == null ? 0 : gp.getCurrentQuantity();
                        if (totalQty > remain) {
                            throw new RuntimeException("GemsPackage not enough. Need " + totalQty + " but have " + remain);
                        }

                        // ✅ DEDUCT
                        gp.setCurrentQuantity(remain - totalQty);
                        gemsPackageRepo.save(gp);

                        item.getJewelleryRows().add(OrderJewellery.builder()
                                .orderItem(item)
                                .gemsPackageId(j.gemsPackageId)
                                .qtyPerUnit(per)
                                .totalQty(totalQty)
                                .build());
                    }
                }

                double line = unit * item.getQty();
                item.setLineTotal(line);
                total += line;

                // custom items usually are made -> in progress
                if (order.getStatus() != OrderStatus.BACKORDERED) {
                    order.setStatus(OrderStatus.IN_PROGRESS);
                }
            }

            order.getItems().add(item);
        }

        order.setTotalPrice(total);

        // if still PENDING and not backorder/custom started → confirm
        if (order.getStatus() == OrderStatus.PENDING) order.setStatus(OrderStatus.CONFIRMED);

        Order saved = orderRepo.save(order);
        return mapper.toDto(saved);
    }

    public OrderDto getById(Long id) {
        Order o = orderRepo.findByIdWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
        return mapper.toDto(o);
    }
}