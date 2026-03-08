package com.real.BanLapTop.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.real.BanLapTop.dto.request.Payment.PaymentRequest;
import com.real.BanLapTop.dto.response.PaymentResponse;
import com.real.BanLapTop.entity.Order;
import com.real.BanLapTop.entity.Payment;
import com.real.BanLapTop.repository.OrderRepository;
import com.real.BanLapTop.repository.PaymentRepository;
import com.real.BanLapTop.service.PaymentService;

@Service
public class PaymentImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentImpl(PaymentRepository paymentRepository,
            OrderRepository orderRepository) {

        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public PaymentResponse create(PaymentRequest request) {

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Payment payment = new Payment();

        payment.setOrder(order);
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setAmount(request.getAmount());

        payment.setStatus("PENDING");
        payment.setTransactionCode(UUID.randomUUID().toString());

        payment.setPaidAt(LocalDateTime.now());

        Payment saved = paymentRepository.save(payment);

        // Fake MoMo redirect
        String payUrl = "http://localhost:3000/momo-payment?paymentId=" + saved.getId();

        PaymentResponse res = new PaymentResponse();

        res.setId(saved.getId());
        res.setOrderId(order.getId());
        res.setAmount(saved.getAmount());
        res.setStatus(saved.getStatus());
        res.setPaymentMethod(saved.getPaymentMethod());
        res.setPayUrl(payUrl);
        return res;
    }
}