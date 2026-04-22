package com.real.BanLapTop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.real.BanLapTop.entity.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByCode(String code);
}