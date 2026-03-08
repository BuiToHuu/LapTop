package com.real.BanLapTop.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private Integer stock;
    private BigDecimal price;
    private String brand;
    private Long categoryid;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    private String imageUrl;

    // getters and setters
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getCategoryId() {
        return categoryid;
    }

    public void setCategoryId(Long category) {
        this.categoryid = category;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;

    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
