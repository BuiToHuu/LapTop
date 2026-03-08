package com.real.BanLapTop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.real.BanLapTop.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

}