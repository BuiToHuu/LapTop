package com.real.BanLapTop.controller;

import com.real.BanLapTop.dto.response.CouponApplyResponse;
import com.real.BanLapTop.dto.response.CouponResponse;
import com.real.BanLapTop.service.CouponService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.real.BanLapTop.dto.request.Coupon.CouponRequest;

@RestController
@RequestMapping("/api/coupons")
@CrossOrigin("*")
public class CouponController {

    private final CouponService service;

    public CouponController(CouponService service) {
        this.service = service;
    }

    @GetMapping
    public List<CouponResponse> getAll() {
        return service.getAll();
    }

    @PostMapping
    public CouponResponse create(@RequestBody CouponRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public CouponResponse update(@PathVariable Long id,
            @RequestBody CouponRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PatchMapping("/{id}/active")
    public void toggle(@PathVariable Long id,
            @RequestBody ToggleRequest req) {
        service.toggleActive(id, req.getIsActive());
    }

    @PostMapping("/apply")
    public CouponApplyResponse applyCoupon(
            @RequestParam String code,
            @RequestParam Double totalPrice) {
        return service.applyCoupon(code, totalPrice);
    }

    // DTO nhỏ cho toggle
    static class ToggleRequest {
        private Boolean isActive;

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }
    }

}