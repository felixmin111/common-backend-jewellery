package com.autowise.demo.service;

import com.autowise.demo.dto.DashboardDto;
import com.autowise.demo.model.Product;
import com.autowise.demo.repository.InvoiceRepository;
import com.autowise.demo.repository.ProductRepository;
import com.autowise.demo.repository.PurchaseItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final InvoiceRepository invoiceRepo;
    private final PurchaseItemRepository purchaseRepo;
    private final ProductRepository productRepo;

    @Transactional
    public DashboardDto getDashboard(
            String filterType,
            String startDate,
            String endDate,
            String month,
            Integer year,
            long lowStockThreshold
    ) {
        LocalDate today = LocalDate.now();

        LocalDateTime from;
        LocalDateTime to;

        if ("range".equalsIgnoreCase(filterType)) {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            from = start.atStartOfDay();
            to = end.plusDays(1).atStartOfDay();

        } else if ("year".equalsIgnoreCase(filterType)) {
            int y = (year != null) ? year : today.getYear();
            LocalDate start = LocalDate.of(y, 1, 1);
            LocalDate end = LocalDate.of(y, 12, 31);
            from = start.atStartOfDay();
            to = end.plusDays(1).atStartOfDay();

        } else {
            String m = (month != null && !month.isBlank())
                    ? month
                    : today.getYear() + "-" + String.format("%02d", today.getMonthValue());

            String[] parts = m.split("-");
            int y = Integer.parseInt(parts[0]);
            int mo = Integer.parseInt(parts[1]);

            LocalDate start = LocalDate.of(y, mo, 1);
            LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

            from = start.atStartOfDay();
            to = end.plusDays(1).atStartOfDay();
        }

        LocalDateTime startToday = today.atStartOfDay();
        LocalDateTime endToday = today.plusDays(1).atStartOfDay();

        BigDecimal todaySales = invoiceRepo.sumConfirmedSalesBetween(startToday, endToday);
        BigDecimal periodSales = invoiceRepo.sumConfirmedSalesBetween(from, to);
        long purchasesCount = invoiceRepo.countConfirmedBetween(from, to);
        long lowStockCount = productRepo.countByQtyLessThanEqual(lowStockThreshold);

        List<Object[]> trendRows = invoiceRepo.dailySalesBetween(from, to);
        List<DashboardDto.SalesPoint> trend = trendRows.stream()
                .map(r -> DashboardDto.SalesPoint.builder()
                        .day(String.valueOf(r[0]))
                        .total((BigDecimal) r[1])
                        .build())
                .toList();

        List<Object[]> topRows = purchaseRepo.topProductsByQty(from, to);

        Set<Long> ids = topRows.stream()
                .map(r -> (Long) r[0])
                .collect(Collectors.toSet());

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
                .todaySales(todaySales != null ? todaySales : BigDecimal.ZERO)
                .monthSales(periodSales != null ? periodSales : BigDecimal.ZERO)
                .purchasesToday(purchasesCount)
                .lowStockCount(lowStockCount)
                .salesTrend(trend)
                .topProducts(top)
                .lowStock(lowStock)
                .build();
    }
}