package com.autowise.demo.dto;

import java.math.BigDecimal;
import java.util.List;

public class PurchaseSaveRequestDto {

    private Long customerId;
    private String status; // Draft / Confirmed
    private BigDecimal discountAmount;
    private BigDecimal discountPercentage;
    private List<ItemDto> items;

    public static class ItemDto {
        private Long productId;
        private Long qty;
        private BigDecimal sellingPrice;

        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }

        public Long getQty() { return qty; }
        public void setQty(Long qty) { this.qty = qty; }

        public BigDecimal getSellingPrice() { return sellingPrice; }
        public void setSellingPrice(BigDecimal sellingPrice) { this.sellingPrice = sellingPrice; }
    }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(BigDecimal discountAmount) { this.discountAmount = discountAmount; }

    public BigDecimal getDiscountPercentage() { return discountPercentage; }
    public void setDiscountPercentage(BigDecimal discountPercentage) { this.discountPercentage = discountPercentage; }

    public List<ItemDto> getItems() { return items; }
    public void setItems(List<ItemDto> items) { this.items = items; }
}