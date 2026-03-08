package com.real.BanLapTop.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentMethod;

    private String transactionCode;

    private BigDecimal amount;

    private String status;

    private LocalDateTime paidAt;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public Payment() {
    }

    public Payment(Long id, String paymentMethod, String transactionCode,
            BigDecimal amount, String status, LocalDateTime paidAt,
            Order order) {
        this.id = id;
        this.paymentMethod = paymentMethod;
        this.transactionCode = transactionCode;
        this.amount = amount;
        this.status = status;
        this.paidAt = paidAt;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public Order getOrder() {
        return order;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}