package com.real.BanLapTop.service.impl;

import com.real.BanLapTop.dto.response.CouponApplyResponse;
import com.real.BanLapTop.dto.response.CouponResponse;
import com.real.BanLapTop.entity.Coupon;
import com.real.BanLapTop.entity.DiscountType;
import com.real.BanLapTop.repository.CouponRepository;
import com.real.BanLapTop.service.CouponService;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.real.BanLapTop.dto.request.Coupon.CouponRequest;

@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository repo;

    public CouponServiceImpl(CouponRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<CouponResponse> getAll() {
        return repo.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CouponResponse create(CouponRequest req) {
        Coupon c = new Coupon();
        mapToEntity(req, c);
        return toResponse(repo.save(c));
    }

    @Override
    public CouponResponse update(Long id, CouponRequest req) {
        Coupon c = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        mapToEntity(req, c);
        return toResponse(repo.save(c));
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void toggleActive(Long id, Boolean isActive) {
        Coupon c = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        c.setIsActive(isActive);
        repo.save(c);
    }

    /* ====== MAPPING ====== */
    private void mapToEntity(CouponRequest req, Coupon c) {
        c.setCode(req.getCode());
        c.setDiscountType(DiscountType.valueOf(req.getDiscountType()));
        c.setDiscountValue(req.getDiscountValue());
        c.setQuantity(req.getQuantity());
        c.setStartDate(req.getStartDate());
        c.setEndDate(req.getEndDate());
        c.setIsActive(req.getIsActive());
    }

    private CouponResponse toResponse(Coupon c) {
        CouponResponse r = new CouponResponse();
        r.setId(c.getId());
        r.setCode(c.getCode());
        r.setDiscountType(c.getDiscountType().name());
        r.setDiscountValue(c.getDiscountValue());
        r.setQuantity(c.getQuantity());
        r.setStartDate(c.getStartDate());
        r.setEndDate(c.getEndDate());
        r.setIsActive(c.getIsActive());
        r.setCreatedAt(c.getCreatedAt());
        return r;
    }

    @Override
    public CouponApplyResponse applyCoupon(String code, Double totalPrice) {

        if (code == null || code.trim().isEmpty()) {
            throw new RuntimeException("Mã không được để trống");
        }

        code = code.trim().toUpperCase();

        Coupon coupon = repo.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Mã không tồn tại"));

        if (!Boolean.TRUE.equals(coupon.getIsActive())) {
            throw new RuntimeException("Mã đã bị khóa");
        }

        if (coupon.getQuantity() != null && coupon.getQuantity() <= 0) {
            throw new RuntimeException("Mã đã hết lượt dùng");
        }

        LocalDateTime now = LocalDateTime.now();
        if (coupon.getStartDate() != null && coupon.getStartDate().isAfter(now)) {
            throw new RuntimeException("Mã chưa bắt đầu");
        }

        if (coupon.getEndDate() != null && coupon.getEndDate().isBefore(now)) {
            throw new RuntimeException("Mã đã hết hạn");
        }

        BigDecimal discountValue = coupon.getDiscountValue();
        if (discountValue == null) {
            throw new RuntimeException("Mã giảm giá không hợp lệ");
        }

        double discount;
        if (DiscountType.PERCENT.equals(coupon.getDiscountType())) {
            discount = totalPrice * discountValue.doubleValue() / 100;
        } else {
            discount = discountValue.doubleValue();
        }

        discount = Math.min(discount, totalPrice);

        if (coupon.getQuantity() != null) {
            coupon.setQuantity(coupon.getQuantity() - 1);
            if (coupon.getQuantity() < 0)
                coupon.setQuantity(0); // bảo vệ DB
            repo.save(coupon);
        }

        return new CouponApplyResponse(coupon.getCode(), discount);
    }
}