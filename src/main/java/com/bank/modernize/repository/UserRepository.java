package com.bank.modernize.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.bank.modernize.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByResetToken(String Token);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUserId(Long userId);

}
