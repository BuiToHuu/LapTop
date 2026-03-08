package com.real.BanLapTop.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.real.BanLapTop.dto.request.OrderItem.OrderItemRequest;
import com.real.BanLapTop.dto.response.OrderItemResponse;
import com.real.BanLapTop.service.OrderItemService;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping
    public OrderItemResponse create(@RequestBody OrderItemRequest request) {

        return orderItemService.create(request);
    }

    @GetMapping("/order/{orderId}")
    public List<OrderItemResponse> getByOrder(@PathVariable Long orderId) {

        return orderItemService.getByOrder(orderId);
    }
}
