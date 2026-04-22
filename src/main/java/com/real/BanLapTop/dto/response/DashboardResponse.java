package com.real.BanLapTop.dto.response;

public class DashboardResponse {

    private long users;
    private long products;
    private long orders;
    private double revenue;

    public DashboardResponse(long users, long products, long orders, double revenue) {
        this.users = users;
        this.products = products;
        this.orders = orders;
        this.revenue = revenue;
    }

    public long getUsers() {
        return users;
    }

    public long getProducts() {
        return products;
    }

    public long getOrders() {
        return orders;
    }

    public double getRevenue() {
        return revenue;
    }
}