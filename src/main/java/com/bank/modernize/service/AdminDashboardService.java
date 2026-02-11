package com.bank.modernize.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bank.modernize.dto.DashboardStatsResponse;
import com.bank.modernize.enums.LoanStatus;
import com.bank.modernize.enums.TxnStatus;
import com.bank.modernize.enums.TxnType;
import com.bank.modernize.repository.LoanRepository;
import com.bank.modernize.repository.TransactionRepository;
import com.bank.modernize.repository.UserRepository;
import com.bank.modernize.entity.Transaction;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final LoanRepository loanRepository;

    public DashboardStatsResponse getDashboardStats() {
        long totalUsers = userRepository.count();
        long totalTransactions = transactionRepository.count();

        List<Transaction> deposits =
                transactionRepository.findByStatusAndTxnType(
                        TxnStatus.SUCCESS, TxnType.DEPOSIT);

        // Revenue = successful deposits
        BigDecimal totalRevenue = deposits.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long pendingLoans = loanRepository.countByStatus(LoanStatus.PENDING);

        return new DashboardStatsResponse(
                totalUsers,
                totalTransactions,
                totalRevenue,
                pendingLoans
        );
    }
}
