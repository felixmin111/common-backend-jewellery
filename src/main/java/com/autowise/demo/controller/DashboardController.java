package com.autowise.demo.controller;

import com.autowise.demo.dto.DashboardDto;
import com.autowise.demo.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public DashboardDto get(
            @RequestParam(defaultValue = "month") String filterType,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String month,
            @RequestParam(required = false) Integer year,
            @RequestParam(defaultValue = "3") long lowStockThreshold
    ) {
        System.out.println("filterType = " + filterType);
        System.out.println("startDate = " + startDate);
        System.out.println("endDate = " + endDate);
        return dashboardService.getDashboard(
                filterType,
                startDate,
                endDate,
                month,
                year,
                lowStockThreshold
        );
    }
}