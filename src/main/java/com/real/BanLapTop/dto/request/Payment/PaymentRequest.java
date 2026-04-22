package com.real.BanLapTop.dto.request.Payment;

import java.math.BigDecimal;

public class PaymentRequest {

    private Long orderId;

    private String paymentMethod;

    private BigDecimal amount;

    public PaymentRequest() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}