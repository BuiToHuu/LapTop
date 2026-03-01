package com.real.BanLapTop.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "branches")
@Data
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "branch_name", length = 150)
    private String branchName;

    private String address;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}