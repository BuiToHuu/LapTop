package com.real.BanLapTop.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // liên kết tới Order
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // snapshot thông tin sản phẩm
    @Column(name = "product_name")
    private String productName;

    private String brand;

    private BigDecimal price;

    private Integer quantity;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public OrderItem() {
    }

    public OrderItem(Long id, Order order, String productName, String brand,
            BigDecimal price, Integer quantity, BigDecimal totalPrice,
            LocalDateTime createdAt) {
        this.id = id;
        this.order = order;
        this.productName = productName;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
    }

    // ======================
    // GETTER
    // ======================

    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public String getProductName() {
        return productName;
    }

    public String getBrand() {
        return brand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // ======================
    // SETTER
    // ======================

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}