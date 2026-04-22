package com.real.BanLapTop.service;

import com.real.BanLapTop.dto.response.CouponResponse;

import java.util.List;

import com.real.BanLapTop.dto.request.Coupon.CouponRequest;
import com.real.BanLapTop.dto.response.CouponApplyResponse;

public interface CouponService {

    List<CouponResponse> getAll();

    CouponResponse create(CouponRequest request);

    CouponResponse update(Long id, CouponRequest request);

    void delete(Long id);

    void toggleActive(Long id, Boolean isActive);

    CouponApplyResponse applyCoupon(String code, Double totalPrice);
}