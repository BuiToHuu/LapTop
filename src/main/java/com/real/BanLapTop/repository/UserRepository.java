package com.real.BanLapTop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.real.BanLapTop.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // Find user by username or email
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);


    // Check if username or email already exists
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
