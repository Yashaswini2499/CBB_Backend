package com.bank.modernize.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.bank.modernize.entity.Transaction;

import java.util.Map;
import java.util.HashMap;
import com.bank.modernize.enums.LoanStatus;


import com.bank.modernize.entity.User;
import com.bank.modernize.service.AdminService;
import com.bank.modernize.repository.TransactionRepository;
import com.bank.modernize.repository.UserRepository;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService service;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;   // ⭐ NEW

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

    // ================= GET ALL USERS =================
    @GetMapping("/all-users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    

    // ================= DELETE USER =================
    @DeleteMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return "User deleted successfully";
    }

        // ================= GET ALL TRANSACTIONS =================
    @GetMapping("/all-transactions")
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAllByOrderByCreatedAtDesc();
    }

    
 // ================= LOAN STATUS STATS (PIE CHART) =================
    @GetMapping("/loan-status-stats")
    public Map<String, Long> getLoanStatusStats() {

        Map<String, Long> stats = new HashMap<>();

        stats.put("APPROVED", service.countLoansByStatus(LoanStatus.APPROVED));
        stats.put("PENDING", service.countLoansByStatus(LoanStatus.PENDING));
        stats.put("REJECTED", service.countLoansByStatus(LoanStatus.REJECTED));

        return stats;
    }


    // ================= MONTHLY TRANSACTIONS (BAR CHART) =================
    @GetMapping("/monthly-transactions")
    public List<Long> getMonthlyTransactions() {
        return transactionRepository.getMonthlyTransactionCounts();
    }
    
    

}