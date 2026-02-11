package com.bank.modernize.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.modernize.dto.DashboardStatsResponse;
import com.bank.modernize.service.AdminDashboardService;

<<<<<<< HEAD
@RestController
@RequestMapping("/admin/dashboard")
=======
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/dashboard")
@RequiredArgsConstructor
>>>>>>> origin/main
public class AdminDashboardController {

    private final AdminDashboardService dashboardService;

<<<<<<< HEAD
    public AdminDashboardController(AdminDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

=======
>>>>>>> origin/main
    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsResponse> getStats() {
        return ResponseEntity.ok(dashboardService.getDashboardStats());
    }
}
