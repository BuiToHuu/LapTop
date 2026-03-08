package com.real.BanLapTop.dto.request.Order;

public class OrderRequest {

    // private Long userId;
    private String name;

    private String shippingAddress;

    private String phone;

    public OrderRequest() {
    }

    // public Long getUserId() {
    // return userId;
    // }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getPhone() {
        return phone;
    }

    // public void setUserId(Long userId) {
    // this.userId = userId;
    // }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}