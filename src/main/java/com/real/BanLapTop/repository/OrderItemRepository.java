package com.real.BanLapTop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.real.BanLapTop.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrderId(Long orderId);

}