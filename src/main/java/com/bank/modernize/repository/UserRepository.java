package com.bank.modernize.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.modernize.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Check if email exists
    boolean existsByEmail(String email);

    // Check if user ID exists (optional)
    boolean existsByUserId(Long userId);
}
