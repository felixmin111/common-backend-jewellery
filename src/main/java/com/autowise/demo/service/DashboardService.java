package com.autowise.demo.service;
import com.autowise.demo.dto.DashboardDto;
import com.autowise.demo.model.Product;
import com.autowise.demo.repository.InvoiceRepository;
import com.autowise.demo.repository.ProductRepository;
import com.autowise.demo.repository.PurchaseItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final InvoiceRepository invoiceRepo;
    private final PurchaseItemRepository purchaseRepo;
    private final ProductRepository productRepo;

    @Transactional
    public DashboardDto getDashboard(int days, long lowStockThreshold) {

        LocalDate today = LocalDate.now();
        LocalDateTime startToday = today.atStartOfDay();
        LocalDateTime endToday = today.plusDays(1).atStartOfDay();

        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        LocalDateTime startMonth = firstDayOfMonth.atStartOfDay();
        LocalDateTime endMonth = today.plusDays(1).atStartOfDay();

        // KPI
        BigDecimal todaySales = invoiceRepo.sumConfirmedSalesBetween(startToday, endToday);
        BigDecimal monthSales = invoiceRepo.sumConfirmedSalesBetween(startMonth, endMonth);
        long purchasesToday = invoiceRepo.countConfirmedBetween(startToday, endToday);
        long lowStockCount = productRepo.countByQtyLessThanEqual(lowStockThreshold);

        // sales trend (days)
        LocalDateTime trendStart = today.minusDays(days - 1L).atStartOfDay();
        LocalDateTime trendEnd = today.plusDays(1).atStartOfDay();

        List<Object[]> trendRows = invoiceRepo.dailySalesBetween(trendStart, trendEnd);
        List<DashboardDto.SalesPoint> trend = trendRows.stream()
                .map(r -> DashboardDto.SalesPoint.builder()
                        .day(String.valueOf(r[0]))
                        .total((BigDecimal) r[1])
                        .build())
                .toList();

        // top products (same range)
        List<Object[]> topRows = purchaseRepo.topProductsByQty(trendStart, trendEnd);

        // map productId -> name (batch fetch)
        Set<Long> ids = topRows.stream().map(r -> (Long) r[0]).collect(Collectors.toSet());
        Map<Long, String> nameMap = productRepo.findAllById(ids).stream()
                .collect(Collectors.toMap(Product::getId, Product::getName));

        List<DashboardDto.TopProduct> top = topRows.stream()
                .limit(10)
                .map(r -> DashboardDto.TopProduct.builder()
                        .productId((Long) r[0])
                        .productName(nameMap.getOrDefault((Long) r[0], String.valueOf(r[0])))
                        .qty(((Number) r[1]).longValue())
                        .build())
                .toList();

        // low stock list
        List<Product> low = productRepo.findTop10ByQtyLessThanEqualOrderByQtyAsc(lowStockThreshold);
        List<DashboardDto.LowStockItem> lowStock = low.stream()
                .map(p -> DashboardDto.LowStockItem.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .code(p.getCode())
                        .qty(p.getQty() == null ? 0 : p.getQty())
                        .build())
                .toList();

        return DashboardDto.builder()
                .todaySales(todaySales)
                .monthSales(monthSales)
                .purchasesToday(purchasesToday)
                .lowStockCount(lowStockCount)
                .salesTrend(trend)
                .topProducts(top)
                .lowStock(lowStock)
                .build();
    }
}