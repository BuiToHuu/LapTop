
// // Test
package com.real.BanLapTop.dto.request.Order;

import java.util.List;

import com.real.BanLapTop.dto.request.OrderItem.OrderItemRequest;

public class OrderRequest {

    private String name;

    private String email;

    private String phone;

    private String shippingAddress;

    private List<OrderItemRequest> items;

    public OrderRequest() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }
}