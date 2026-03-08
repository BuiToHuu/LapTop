package com.real.BanLapTop.controller;

import java.util.List;

import com.real.BanLapTop.dto.request.Order.OrderRequest;
import com.real.BanLapTop.dto.response.OrderResponse;

import com.real.BanLapTop.service.OrderService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")

public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderResponse create(@RequestBody OrderRequest request) {
        return orderService.create(request);
    }

    @GetMapping("/my")
    public List<OrderResponse> getMyOrders() {
        return orderService.getMyOrders();
    }

}