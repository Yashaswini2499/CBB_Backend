package com.bank.modernize.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.bank.modernize.repository.UserRepository;
import com.bank.modernize.repository.LoanRepository;
import com.bank.modernize.enums.LoanStatus;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepo;
    private final LoanRepository loanRepository;

    // ================= TOTAL USERS =================
    public long getTotalUsers() {
        return userRepo.count();
    }

    // ================= PENDING LOANS =================
    public long getPendingLoans() {
        return loanRepository.countByStatus(LoanStatus.PENDING);
    }

    // ================= DELETE USER =================
    public void deleteUser(Long userId) {
        if (!userRepo.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        userRepo.deleteById(userId);
    }
}