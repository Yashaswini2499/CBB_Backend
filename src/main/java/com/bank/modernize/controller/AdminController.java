package com.bank.modernize.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}