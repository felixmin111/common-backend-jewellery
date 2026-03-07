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
            @RequestParam(defaultValue = "7") int days,
            @RequestParam(defaultValue = "3") long lowStockThreshold
    ) {
        return dashboardService.getDashboard(days, lowStockThreshold);
    }
}