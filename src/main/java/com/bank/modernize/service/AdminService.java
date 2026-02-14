package com.bank.modernize.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.modernize.entity.User;
import com.bank.modernize.enums.LoanStatus;
import com.bank.modernize.enums.Role;
import com.bank.modernize.repository.AccountRepository;
import com.bank.modernize.repository.LoanRepository;
import com.bank.modernize.repository.TransactionRepository;
import com.bank.modernize.repository.UserRepository;
import com.bank.modernize.repository.EmiPaymentRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final UserRepository userRepo;
    private final LoanRepository loanRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final EmiPaymentRepository emiPaymentRepository;

    // ================= TOTAL USERS =================
    public long getTotalUsers() {
        return userRepo.count();
    }

    // ================= PENDING LOANS =================
    public long getPendingLoans() {
        return loanRepository.countByStatus(LoanStatus.PENDING);
    }

    public long countLoansByStatus(LoanStatus status) {
        return loanRepository.countByStatus(status);
    }

    // ================= DELETE USER (SAFE CASCADE) =================
    public String deleteUser(Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ❌ Prevent deleting ADMIN
        if (user.getRole() == Role.ADMIN) {
            throw new RuntimeException("Admin cannot be deleted");
        }

        String name = user.getFullName();

        // ================= DELETE IN SAFE ORDER =================

        // 1️⃣ Delete EMI (loan child)
        emiPaymentRepository.deleteByUserId(userId);

        // 2️⃣ Delete Transactions (account child)
        transactionRepository.deleteByUserId(userId);

        // 3️⃣ Delete Loans
        loanRepository.deleteByCustomerId(userId);

        // 4️⃣ Delete Accounts
        accountRepository.deleteByCustomerId(userId);

        // 5️⃣ Delete User
        userRepo.deleteById(userId);

        return name + " deleted successfully";
    }
}