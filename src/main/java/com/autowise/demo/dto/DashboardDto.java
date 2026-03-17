package com.autowise.demo.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardDto {

    private BigDecimal todaySales;
    private BigDecimal monthSales;
    private long purchasesToday;
    private long lowStockCount;

    private List<SalesPoint> salesTrend;
    private List<TopProduct> topProducts;
    private List<LowStockItem> lowStock;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SalesPoint {
        private String day;
        private BigDecimal total;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TopProduct {
        private Long productId;
        private String productName;
        private Long qty;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class LowStockItem {
        private Long id;
        private String name;
        private String code;
        private Long qty;
    }
}