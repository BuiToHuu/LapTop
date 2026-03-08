package com.real.BanLapTop.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.real.BanLapTop.dto.request.OrderItem.OrderItemRequest;
import com.real.BanLapTop.dto.response.OrderItemResponse;
import com.real.BanLapTop.entity.Order;
import com.real.BanLapTop.entity.OrderItem;
import com.real.BanLapTop.entity.Product;
import com.real.BanLapTop.repository.OrderItemRepository;
import com.real.BanLapTop.repository.OrderRepository;
import com.real.BanLapTop.repository.ProductRepository;
import com.real.BanLapTop.service.OrderItemService;

@Service
public class OrderItemImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderItemImpl(
            OrderItemRepository orderItemRepository,
            OrderRepository orderRepository,
            ProductRepository productRepository) {

        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public OrderItemResponse create(OrderItemRequest request) {

        // tìm order
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // tìm product
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        OrderItem item = new OrderItem();

        item.setOrder(order);
        item.setProductName(product.getName());
        item.setBrand(product.getBrand());

        // lấy giá từ database (an toàn hơn)
        item.setPrice(product.getPrice());

        item.setQuantity(request.getQuantity());

        BigDecimal total = product.getPrice()
                .multiply(BigDecimal.valueOf(request.getQuantity()));

        item.setTotalPrice(total);
        item.setCreatedAt(LocalDateTime.now());

        OrderItem saved = orderItemRepository.save(item);

        return mapToResponse(saved);
    }

    @Override
    public List<OrderItemResponse> getByOrder(Long orderId) {

        return orderItemRepository.findByOrderId(orderId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private OrderItemResponse mapToResponse(OrderItem item) {

        OrderItemResponse res = new OrderItemResponse();

        res.setId(item.getId());
        res.setProductName(item.getProductName());
        res.setBrand(item.getBrand());
        res.setPrice(item.getPrice());
        res.setQuantity(item.getQuantity());
        res.setTotalPrice(item.getTotalPrice());

        return res;
    }
}