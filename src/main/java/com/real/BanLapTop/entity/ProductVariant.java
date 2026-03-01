package com.real.BanLapTop.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_variants")
@Data
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String cpu;
    private String ram;
    private String ssd;
    private String gpu;
    private String screen;
    private String color;

    @Column(precision = 15, scale = 2)
    private BigDecimal price;

    @Column(unique = true, length = 100)
    private String sku;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}