package com.real.BanLapTop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.real.BanLapTop.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUserId(Long userId);

    Optional<CartItem> findByUserIdAndProductId(Long userId, Long productId);

    void deleteByUserId(Long userId);

}