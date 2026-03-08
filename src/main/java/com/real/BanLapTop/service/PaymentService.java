package com.real.BanLapTop.service;

import com.real.BanLapTop.dto.request.Payment.PaymentRequest;
import com.real.BanLapTop.dto.response.PaymentResponse;

public interface PaymentService {

    PaymentResponse create(PaymentRequest request);

}