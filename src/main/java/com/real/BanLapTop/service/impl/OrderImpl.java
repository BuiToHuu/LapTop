package com.real.BanLapTop.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.real.BanLapTop.dto.request.Order.OrderRequest;
import com.real.BanLapTop.dto.response.OrderResponse;
import com.real.BanLapTop.entity.CartItem;
import com.real.BanLapTop.entity.Order;
import com.real.BanLapTop.entity.OrderStatus;
import com.real.BanLapTop.entity.User;
import com.real.BanLapTop.repository.CartItemRepository;
import com.real.BanLapTop.repository.OrderRepository;
import com.real.BanLapTop.repository.UserRepository;
import com.real.BanLapTop.service.EmailService;
import com.real.BanLapTop.service.OrderService;

@Service
public class OrderImpl implements OrderService {
    @Autowired
    private EmailService emailService;

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    public OrderImpl(
            OrderRepository orderRepository,
            UserRepository userRepository,
            CartItemRepository cartItemRepository) {

        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
    }

    private OrderResponse mapToResponse(Order order) {

        OrderResponse res = new OrderResponse();

        res.setId(order.getId());
        res.setUserId(order.getUser().getId());
        res.setTotalPrice(order.getTotalPrice());
        res.setStatus(order.getStatus().name());
        res.setShippingAddress(order.getShippingAddress());
        res.setPhone(order.getPhone());

        return res;
    }

    @Override
    public OrderResponse create(OrderRequest request) {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || authentication.getName().equals("anonymousUser")) {

            throw new RuntimeException("User not authenticated");
        }

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // lấy cart item
        List<CartItem> cartItems = cartItemRepository.findByUserId(user.getId());

        if (cartItems == null || cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty!");
        }

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : cartItems) {

            if (item.getProduct() == null) {
                throw new RuntimeException("Product missing in cart");
            }

            if (item.getProduct().getPrice() == null) {
                throw new RuntimeException("Product price is null");
            }

            BigDecimal price = item.getProduct().getPrice();
            BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());

            total = total.add(price.multiply(quantity));
        }

        Order order = new Order();

        order.setUser(user);
        order.setShippingAddress(request.getShippingAddress());
        order.setPhone(request.getPhone());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalPrice(total);
        order.setCreatedAt(LocalDateTime.now());
        order.setName(request.getName());

        Order savedOrder = orderRepository.save(order);

        // Sent Email
        emailService.sendEmail(
                "buitohuu123@gmail.com",
                "Đơn hàng mới",
                "Có đơn hàng mới từ user: " + user.getUsername());

        // clear cart
        cartItemRepository.deleteAll(cartItems);

        return mapToResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getMyOrders() {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orders = orderRepository.findByUserId(user.getId());

        return orders.stream()
                .map(this::mapToResponse)
                .toList();
    }

}