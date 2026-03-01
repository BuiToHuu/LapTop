package com.real.BanLapTop.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
@Data
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    private ProductVariant variant;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    private Integer quantity;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}