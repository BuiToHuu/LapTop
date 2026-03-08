package com.real.BanLapTop.dto.response;

import java.math.BigDecimal;

public class PaymentResponse {

    private Long id;

    private Long orderId;

    private String paymentMethod;

    private String transactionCode;

    private BigDecimal amount;

    private String status;

    private String payUrl;

    public PaymentResponse() {
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
}