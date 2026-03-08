package com.real.BanLapTop.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderResponse {

    private Long id;

    private Long userId;

    private BigDecimal totalPrice;

    private String status;

    private String shippingAddress;

    private String phone;

    private LocalDateTime createdAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OrderResponse() {
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}