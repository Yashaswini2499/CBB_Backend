package com.bank.modernize.dto;

import java.math.BigDecimal;

public class DashboardStatsResponse {
    private long totalUsers;
    private long totalTransactions;
    private BigDecimal totalRevenue;
    private long pendingLoans;

    public DashboardStatsResponse(long totalUsers, long totalTransactions, BigDecimal totalRevenue, long pendingLoans) {
        this.totalUsers = totalUsers;
        this.totalTransactions = totalTransactions;
        this.totalRevenue = totalRevenue;
        this.pendingLoans = pendingLoans;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(long totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public long getPendingLoans() {
        return pendingLoans;
    }

    public void setPendingLoans(long pendingLoans) {
        this.pendingLoans = pendingLoans;
    }
}
