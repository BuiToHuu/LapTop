package com.real.BanLapTop.service.impl;

import org.springframework.stereotype.Service;

import com.real.BanLapTop.dto.response.DashboardResponse;
import com.real.BanLapTop.repository.UserRepository;
import com.real.BanLapTop.repository.ProductRepository;
import com.real.BanLapTop.repository.OrderRepository;
import com.real.BanLapTop.repository.PaymentRepository;

@Service
public class DashboardService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public DashboardService(UserRepository userRepository,
            ProductRepository productRepository,
            OrderRepository orderRepository,
            PaymentRepository paymentRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    public DashboardResponse getDashboardStats() {

        long users = userRepository.count();
        long products = productRepository.count();
        long orders = orderRepository.count();
        double revenue = paymentRepository.getTotalRevenue();

        return new DashboardResponse(users, products, orders, revenue);
    }
}