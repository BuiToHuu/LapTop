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

    //////////////
    // test Guest
    @PostMapping("/guest")
    public OrderResponse createGuest(@RequestBody OrderRequest request) {
        return orderService.createGuestOrder(request);
    }

    ////////////// TEST 2
    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PutMapping("/{id}/status")
    public OrderResponse updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return orderService.updateStatus(id, status);
    }

    @PutMapping("/{id}/cancel")
    public OrderResponse cancelOrder(@PathVariable Long id) {
        return orderService.cancelOrder(id);
    }
}