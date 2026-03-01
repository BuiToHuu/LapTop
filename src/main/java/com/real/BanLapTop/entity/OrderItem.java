package com.real.BanLapTop.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_items")
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    private ProductVariant variant;

    private Integer quantity;

    @Column(name = "price_at_purchase", precision = 15, scale = 2)
    private BigDecimal priceAtPurchase;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}