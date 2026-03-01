package com.real.BanLapTop.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;


@Entity
@Table(name = "cart_items")
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    private ProductVariant variant;

    private Integer quantity;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}