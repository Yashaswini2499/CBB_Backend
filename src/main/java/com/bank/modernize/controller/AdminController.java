package com.bank.modernize.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import com.bank.modernize.entity.Transaction;
import com.bank.modernize.entity.User;
import com.bank.modernize.enums.LoanStatus;
import com.bank.modernize.enums.Role;
import com.bank.modernize.service.AdminService;
import com.bank.modernize.repository.TransactionRepository;
import com.bank.modernize.repository.UserRepository;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService service;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    // ================= TOTAL USERS =================
    @GetMapping("/total-users")
    public long totalUsers() {
        return service.getTotalUsers();
    }

    // ================= TOTAL REVENUE =================
    @GetMapping("/total-revenue")
    public Double getTotalRevenue() {
        return transactionRepository.getTotalRevenue();
    }

    // ================= PENDING LOANS =================
    @GetMapping("/pending-loans")
    public long getPendingLoans() {
        return service.getPendingLoans();
    }

    // ================= TODAY TRANSACTIONS =================
    @GetMapping("/today-transactions")
    public long getTodayTransactions() {
        return transactionRepository.countTodayTransactions();
    }

    // ================= GET ONLY CUSTOMER USERS (NO ADMIN) =================
    @GetMapping("/all-users")
    public List<User> getAllUsers() {
        return userRepository.findByRoleNot(Role.ADMIN);   // ✅ FIXED
    }

    // ================= DELETE USER =================
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        String msg = service.deleteUser(id);
        return ResponseEntity.ok(msg);
    }

    // ================= GET ALL TRANSACTIONS =================
    @GetMapping("/all-transactions")
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAllByOrderByCreatedAtDesc();
    }

    // ================= LOAN STATUS STATS =================
    @GetMapping("/loan-status-stats")
    public Map<String, Long> getLoanStatusStats() {

        Map<String, Long> stats = new HashMap<>();

        stats.put("Approved", service.countLoansByStatus(LoanStatus.APPROVED));
        stats.put("Pending", service.countLoansByStatus(LoanStatus.PENDING));
        stats.put("Rejected", service.countLoansByStatus(LoanStatus.REJECTED));

        return stats;
    }

    // ================= MONTHLY TRANSACTIONS =================
    @GetMapping("/monthly-transactions")
    public List<Map<String, Object>> getMonthlyTransactions() {

        List<Object[]> rows = transactionRepository.getMonthlyTransactionStats();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] row : rows) {
            Map<String, Object> map = new HashMap<>();
            map.put("day", "Day " + row[0]);
            map.put("transactions", ((Number) row[1]).intValue());
            result.add(map);
        }

        return result;
    }
}