package com.real.BanLapTop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.real.BanLapTop.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Find user by username or email
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    // Check if username or email already exists
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
