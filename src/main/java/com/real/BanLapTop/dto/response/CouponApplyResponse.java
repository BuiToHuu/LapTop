package com.real.BanLapTop.dto.response;

public class CouponApplyResponse {

    private String code;
    private Double discountAmount;

    // optional (xịn hơn)
    private String discountType;
    private Double discountValue;

    public CouponApplyResponse() {
    }

    public CouponApplyResponse(String code, Double discountAmount) {
        this.code = code;
        this.discountAmount = discountAmount;
    }

    // ===== getter setter =====
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }
}