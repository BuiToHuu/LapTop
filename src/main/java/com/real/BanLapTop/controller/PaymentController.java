package com.real.BanLapTop.controller;

import org.springframework.web.bind.annotation.*;

import com.real.BanLapTop.dto.request.Payment.PaymentRequest;
import com.real.BanLapTop.dto.response.PaymentResponse;
import com.real.BanLapTop.entity.OrderStatus;
import com.real.BanLapTop.entity.Payment;
import com.real.BanLapTop.repository.PaymentRepository;
import com.real.BanLapTop.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;

    public PaymentController(PaymentService paymentService,
            PaymentRepository paymentRepository) {
        this.paymentService = paymentService;
        this.paymentRepository = paymentRepository;
    }

    @PostMapping
    public PaymentResponse create(@RequestBody PaymentRequest request) {
        return paymentService.create(request);
    }

    @PutMapping("/success/{paymentId}")
    public String paymentSuccess(@PathVariable Long paymentId) {

        Payment payment = paymentRepository
                .findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        // cập nhật payment
        payment.setStatus("SUCCESS");

        // cập nhật order
        payment.getOrder().setStatus(OrderStatus.PAID);

        paymentRepository.save(payment);

        return "OK";
    }
}