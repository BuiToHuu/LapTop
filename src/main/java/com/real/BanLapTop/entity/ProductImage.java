package com.real.BanLapTop.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_images")
@Data
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "is_thumbnail")
    private Boolean isThumbnail = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}