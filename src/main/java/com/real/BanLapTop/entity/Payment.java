package com.real.BanLapTop.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne // Mỗi đơn hàng thường có 1 giao dịch thanh toán
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "payment_method", length = 50)
    private String paymentMethod; // COD, VNPAY, MOMO

    @Column(name = "transaction_code", length = 150)
    private String transactionCode;

    @Column(precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(length = 50)
    private String status; // PENDING, SUCCESS, FAILED

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}